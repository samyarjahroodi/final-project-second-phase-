package finalproject.finalproject.repository;


import finalproject.finalproject.model.user.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PersonRepository<Customer> {
    Customer getId(String username, String password);
}
