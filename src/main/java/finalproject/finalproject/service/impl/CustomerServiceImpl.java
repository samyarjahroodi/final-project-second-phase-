package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl
        extends PersonServiceImpl<Customer, CustomerRepository>
        implements CustomerService {


    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }

    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username,password);
    }
}