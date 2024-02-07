package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.Entity.operation.Comment;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.payment.Wallet;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.exception.*;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.CustomerService;
import finalproject.finalproject.service.dto.request.CommentDtoRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl
        extends PersonServiceImpl<Customer, CustomerRepository>
        implements CustomerService {

    private final WalletServiceImpl walletService;
    private final CustomerOrderServiceImpl customerOrderService;
    private final ExpertServiceImpl expertService;
    private final CommentServiceImpl commentService;

    public CustomerServiceImpl(CustomerRepository repository, WalletRepository walletRepository, WalletServiceImpl walletService,
                               CustomerOrderServiceImpl customerOrderService, ExpertServiceImpl expertService, CommentServiceImpl commentService) {
        super(repository);
        this.walletService = walletService;
        this.customerOrderService = customerOrderService;
        this.expertService = expertService;
        this.commentService = commentService;
    }

    //this method is for checking login
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
    public void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder, LocalDate localDate) {
        if (customerOrder == null) {
            throw new NullInputException("customerOrder cannot be null");
        }
        customerOrder.setStatus(Status.FINISHED);
        customerOrder.setTimeThatStatusChangedToFinished(localDate);
    }

    public Customer createCustomer(UserDtoRequest dto) {
        if (dto == null) {
            throw new NullInputException("dto cannot be null");
        }
        Customer customer = Customer.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .wallet(walletService.save(Wallet.builder().creditOfWallet(0).build()))
                .build();

        return repository.save(customer);
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
        if (customerOrder == null) {
            throw new NullInputException("customer Order cannot be null");
        }
        if (customerOrder.getStatus() == Status.FINISHED) {
            double priceOfTheCustomerOrder = customerOrder.getPrice();
            Customer customer = customerOrder.getCustomer();
            Expert expert = findApprovedExpert(customerOrder);

            if (expert != null) {
                Wallet customerWallet = customer.getWallet();
                Wallet expertWallet = expert.getWallet();

                double customerCredit = customerWallet.getCreditOfWallet();

                if (customerCredit >= priceOfTheCustomerOrder) {
                    double expertShare = priceOfTheCustomerOrder * 0.7;

                    customerWallet.setCreditOfWallet(customerCredit - priceOfTheCustomerOrder);
                    expertWallet.setCreditOfWallet(expertWallet.getCreditOfWallet() + expertShare);
                    customerOrder.setStatus(Status.BEEN_PAID);
                    customerOrderService.save(customerOrder);
                } else {
                    throw new NotEnoughCreditException("Customer does not have enough credit to pay");
                }
            } else {
                throw new StatusException("No approved expert found for this order");
            }
        } else {
            throw new StatusException("your order has not done yet");
        }
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



    public Expert findApprovedExpert(CustomerOrder customerOrder) {
        return customerOrder.getSuggestions().stream()
                .filter(Suggestion::getIsApproved)
                .map(Suggestion::getExpert)
                .findFirst()
                .orElse(null);
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
        return repository.getReferenceById(integer);
    }
}
