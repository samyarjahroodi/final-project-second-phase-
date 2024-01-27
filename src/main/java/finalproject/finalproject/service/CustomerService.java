package finalproject.finalproject.service;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Customer;
import org.springframework.stereotype.Service;

@Service

public interface CustomerService extends PersonService<Customer> {
    Customer findByUsernameAndPassword(String username, String password);

    void changeStatusOfCustomerOrder(CustomerOrder customerOrder, Customer customer);

    void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder, Customer customer);

}
