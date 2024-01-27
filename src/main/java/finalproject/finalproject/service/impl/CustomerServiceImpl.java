package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.operation.CustomerOrder;
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
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void changeStatusOfCustomerOrder(CustomerOrder customerOrder, Customer customer) {
        repository.changeStatusOfCustomerOrderToStarted(customerOrder, customer);
    }

    @Override
    public void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder, Customer customer) {
        repository.changeStatusOfCustomerOrderToFinished(customerOrder, customer);
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
        for (Customer c : repository.findAll()) {
            if (username.equals(c.getUsername())) {
                for (Customer c1 : repository.findAll()) {
                    if (c1.getPassword().equals(oldPassword)) {
                        Customer customer = repository.findByUsernameAndPassword(username, oldPassword);
                        customer.setPassword(newPassword);
                        repository.save(customer);
                        return;
                    } else {
                        throw new IllegalArgumentException("your password is not strong");
                    }
                }
            }
        }
        throw new NullPointerException("null or empty username");
    }
}
