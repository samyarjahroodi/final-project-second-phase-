package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.payment.Wallet;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.Role;
import finalproject.finalproject.exception.*;
import finalproject.finalproject.repository.ConfirmationTokenRepository;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.service.CustomerService;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static finalproject.finalproject.service.validation.ValidateUserDto.validateUserDtoRequest;


@Service
@Transactional
public class CustomerServiceImpl
        extends PersonServiceImpl<Customer, CustomerRepository>
        implements CustomerService {

    private final WalletServiceImpl walletService;
    private final CustomerOrderServiceImpl customerOrderService;
    private final ExpertServiceImpl expertService;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository repository, BCryptPasswordEncoder passwordEncoder, ConfirmationTokenRepository confirmationTokenRepository, EmailServiceImpl emailService, JavaMailSender mailSender, WalletServiceImpl walletService, CustomerOrderServiceImpl customerOrderService, ExpertServiceImpl expertService, BCryptPasswordEncoder passwordEncoder1) {
        super(repository, passwordEncoder, confirmationTokenRepository, emailService, mailSender);
        this.walletService = walletService;
        this.customerOrderService = customerOrderService;
        this.expertService = expertService;
        this.passwordEncoder = passwordEncoder1;
    }


    public Customer createCustomer(UserDtoRequest dto, String siteURL) throws MessagingException {
        validateUserDtoRequest(dto);
        Customer customer = Customer.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .wallet(walletService.save(Wallet.builder().creditOfWallet(0).build()))
                .role(Role.ROLE_CUSTOMER)
                .creationDate(LocalDate.now())
                .build();
        Customer save = repository.save(customer);
        sendEmail(dto.getEmail());
        return save;
    }


    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        //This method is for login for different user types
        if (username == null || password == null) {
            throw new NullInputException("username or password cannot be null");
        }
        return repository.findByUsernameAndPassword(username, password);
    }


    @Override
    public void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            throw new NullInputException("customerOrder cannot be null");
        }
        customerOrder.setStatus(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE);
    }

    @Override
    public void changeStatusToStarted(CustomerOrder customerOrder, Suggestion suggestion, LocalDate timeToStartTheProject) {
        if (customerOrder == null || suggestion == null || timeToStartTheProject == null) {
            throw new NullInputException("customerOrder,suggestion,time to start the project cannot be null");
        }
        if (suggestion.getWhenSuggestionCreated().isAfter(timeToStartTheProject)) {
            throw new TimeException("your time should be after created time for suggestion");
        }
        customerOrder.setStatus(Status.STARTED);
    }

    //local date should be now but for testing we need to get local date from customer
    @Override
    public void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder, ZonedDateTime zonedDateTime) {
        if (customerOrder == null) {
            throw new NullInputException("customerOrder cannot be null");
        }
        customerOrder.setStatus(Status.FINISHED);
        customerOrder.setTimeThatStatusChangedToFinished(zonedDateTime);
        customerOrderService.save(customerOrder);
    }


    public String changePassword(String username, String oldPassword, String password) {
        if (username == null || oldPassword == null || password == null) {
            throw new NullInputException("username , old password , new password cannot be null");
        }
        Customer customer = repository.findAll().stream()
                .filter(c -> username.equals(c.getUsername()))
                .filter(c1 -> c1.getPassword().equals(oldPassword))
                .findFirst()
                .orElseThrow(() -> new InvalidUsernameOrPasswordException("Invalid username or password"));

        customer.setPassword(password);
        repository.save(customer);
        return password;
    }


    @Override
    public void payThePriceOfCustomerOrderByWallet(CustomerOrder customerOrder) {
        validateCustomerOrder(customerOrder);
        double priceOfTheCustomerOrder = customerOrder.getPrice();
        Customer customer = customerOrder.getCustomer();
        Expert expert = findApprovedExpert(customerOrder);
        checkApprovedExpert(expert);
        Wallet customerWallet = customer.getWallet();
        Wallet expertWallet = expert.getWallet();
        double customerCredit = customerWallet.getCreditOfWallet();
        ensureCustomerHasEnoughCredit(customerCredit, priceOfTheCustomerOrder);

        double expertShare = calculateExpertShare(priceOfTheCustomerOrder);
        updateWalletsAndOrderStatus(customerWallet, expertWallet, expertShare, customerOrder);
    }

    private void validateCustomerOrder(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            throw new NullInputException("Customer Order cannot be null");
        }
        if (customerOrder.getStatus() != Status.FINISHED) {
            throw new StatusException("Your order has not been finished yet");
        }
    }

    private void checkApprovedExpert(Expert expert) {
        if (expert == null) {
            throw new StatusException("No approved expert found for this order");
        }
    }

    private void ensureCustomerHasEnoughCredit(double customerCredit, double priceOfTheCustomerOrder) {
        if (customerCredit < priceOfTheCustomerOrder) {
            throw new NotEnoughCreditException("Customer does not have enough credit to pay");
        }
    }

    private double calculateExpertShare(double priceOfTheCustomerOrder) {
        return priceOfTheCustomerOrder * 0.7;
    }

    private void updateWalletsAndOrderStatus(Wallet customerWallet, Wallet expertWallet, double expertShare, CustomerOrder customerOrder) {
        double customerCredit = customerWallet.getCreditOfWallet();
        customerWallet.setCreditOfWallet(customerCredit - customerOrder.getPrice());
        expertWallet.setCreditOfWallet(expertWallet.getCreditOfWallet() + expertShare);
        walletService.save(expertWallet);
        customerOrder.setStatus(Status.BEEN_PAID);
        walletService.save(customerWallet);
        customerOrderService.save(customerOrder);
    }


    @Modifying
    @Override
    public void payThePriceOfCustomerOrderOnline(CustomerOrder customerOrder, Card card) {
        if (customerOrder == null || card == null) {
            throw new NullInputException("customer Order and card cannot be null");
        }
        if (customerOrder.getStatus().equals(Status.FINISHED)) {
            Suggestion suggestionThatIsApproved = customerOrderService.findSuggestionThatIsApproved(customerOrder);
            if (card.getMoney() > suggestionThatIsApproved.getSuggestedPrice()) {
                int suggestedPrice = suggestionThatIsApproved.getSuggestedPrice();
                Expert approvedExpert = findApprovedExpert(customerOrder);
                card.setMoney(card.getMoney() - suggestionThatIsApproved.getSuggestedPrice());
                double creditOfWallet = approvedExpert.getWallet().getCreditOfWallet();
                approvedExpert.getWallet().setCreditOfWallet(creditOfWallet + 0.7 * suggestedPrice);
                customerOrder.setStatus(Status.BEEN_PAID);
                customerOrderService.save(customerOrder);
                expertService.save(approvedExpert);
            }
        } else {
            throw new StatusException("customer order status should be finished");
        }
    }

    @Override
    public List<CustomerOrder> showAllOrders(Customer customer) {
        return customer.getOrders();
    }


    public Expert findApprovedExpert(CustomerOrder customerOrder) {
        return customerOrder.getSuggestions().get(0).getExpert();
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Integer integer) {
        return repository.findById(integer);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Customer> findAllById(Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    @Override
    public List<Customer> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    @Override
    public void delete(Customer entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Customer getReferenceById(Integer integer) {
        return repository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }
}
