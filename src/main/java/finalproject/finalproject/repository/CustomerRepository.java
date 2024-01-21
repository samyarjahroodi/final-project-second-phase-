package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.user.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends PersonRepository<Customer> {
    Customer findByUsernameAndPassword(String username, String password);
}
