package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.user.Customer;
import repository.CustomerRepository;
import service.CustomerService;

public class CustomerServiceImpl
        extends BaseEntityServiceImpl<Customer, Integer, CustomerRepository>
        implements CustomerService {

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }

    @Override
    public Customer getId(String username, String password) {
        return repository.getId(username,password);
    }
}