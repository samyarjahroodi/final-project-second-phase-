package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository
        extends PersonRepository<Customer> {
    Customer findByUsernameAndPassword(String username, String password);

  /*  @Query("SELECT co.price, co.status FROM CustomerOrder co WHERE co.customer = :customer")
    List<Object[]> findPriceAndStatusByCustomer(Customer customer);*/


}
