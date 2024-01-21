package finalproject.finalproject.service;


import finalproject.finalproject.Entity.user.Customer;

public interface CustomerService extends PersonService<Customer> {
    Customer findByUsernameAndPassword(String username, String password);
}
