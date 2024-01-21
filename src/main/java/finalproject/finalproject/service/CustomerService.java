package finalproject.finalproject.service;


import finalproject.finalproject.model.user.Customer;

public interface CustomerService extends PersonService<Customer> {
    Customer getId(String username, String password);
}
