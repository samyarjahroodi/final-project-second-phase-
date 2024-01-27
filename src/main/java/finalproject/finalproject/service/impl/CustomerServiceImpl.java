package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.utility.Wallet;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.CustomerService;
import finalproject.finalproject.service.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl
        extends PersonServiceImpl<Customer, CustomerRepository>
        implements CustomerService {

    private final WalletRepository walletRepository;

    public CustomerServiceImpl(CustomerRepository repository, WalletRepository walletRepository) {
        super(repository);
        this.walletRepository = walletRepository;
    }

    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        //This method is for login for different user types
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(CustomerOrder customerOrder) {
        customerOrder.setStatus(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE);
    }

    @Override
    public void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder) {
        customerOrder.setStatus(Status.FINISHED);
    }

    public void createCustomer(UserDto dto) {
        Customer customer = new Customer();
        customer.setFirstname(dto.getFirstname());
        customer.setLastname(dto.getLastname());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setUsername(dto.getUsername());
        Wallet wallet = new Wallet();
        wallet.setCreditOfWallet(0);
        walletRepository.save(wallet);
        customer.setWallet(wallet);
        repository.save(customer);
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        Customer customer = repository.findAll().stream()
                .filter(c -> username.equals(c.getUsername()))
                .filter(c1 -> c1.getPassword().equals(oldPassword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        customer.setPassword(newPassword);
        repository.save(customer);
    }

}
