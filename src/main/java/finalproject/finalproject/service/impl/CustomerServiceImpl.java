package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.utility.Wallet;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.CustomerService;
import finalproject.finalproject.service.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class CustomerServiceImpl
        extends PersonServiceImpl<Customer, CustomerRepository>
        implements CustomerService {

    private final WalletRepository walletRepository;

    public CustomerServiceImpl(CustomerRepository repository, WalletRepository walletRepository) {
        super(repository);
        this.walletRepository = walletRepository;
    }

    //this method is for checking login
    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        //This method is for login for different user types
        if (username == null || password == null) {
            throw new IllegalArgumentException("username or password cannot be null");
        }
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            throw new IllegalArgumentException("customerOrder cannot be null");
        }
        customerOrder.setStatus(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE);
    }

    public void changeStatusToStarted(CustomerOrder customerOrder, Suggestion suggestion, LocalDate timeToStartTheProject) {
        if (customerOrder == null || suggestion == null || timeToStartTheProject == null) {
            throw new IllegalArgumentException("customerOrder,suggestion,time to start the project cannot be null");
        }
        if (suggestion.getWhenSuggestionCreated().isAfter(timeToStartTheProject)) {
            throw new IllegalArgumentException("your time should be after created time for suggestion");
        }
        customerOrder.setStatus(Status.STARTED);
    }

    @Override
    public void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            throw new IllegalArgumentException("customerOrder cannot be null");
        }
        customerOrder.setStatus(Status.FINISHED);
    }

    public void createCustomer(UserDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto cannot be null");
        }
        Customer customer = Customer.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .wallet(walletRepository.save(Wallet.builder().creditOfWallet(0).build()))
                .build();

        repository.save(customer);
    }


    public void changePassword(String username, String oldPassword, String newPassword) {
        if (username == null || oldPassword == null || newPassword == null) {
            throw new IllegalArgumentException("username , old password , new password cannot be null");
        }
        Customer customer = repository.findAll().stream()
                .filter(c -> username.equals(c.getUsername()))
                .filter(c1 -> c1.getPassword().equals(oldPassword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        customer.setPassword(newPassword);
        repository.save(customer);
    }

}
