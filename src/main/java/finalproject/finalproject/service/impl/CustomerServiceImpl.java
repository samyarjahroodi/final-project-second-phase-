package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.Wallet;
import finalproject.finalproject.exception.InvalidUsernameOrPasswordException;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.exception.TimeException;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.CustomerService;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public CustomerServiceImpl(CustomerRepository repository, WalletRepository walletRepository, WalletServiceImpl walletService) {
        super(repository);
        this.walletService = walletService;
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

    @Override
    public void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            throw new NullInputException("customerOrder cannot be null");
        }
        customerOrder.setStatus(Status.FINISHED);
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
