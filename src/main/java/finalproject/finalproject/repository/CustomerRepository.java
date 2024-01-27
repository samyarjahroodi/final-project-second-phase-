package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository
        extends PersonRepository<Customer> {
    Customer findByUsernameAndPassword(String username, String password);

    @Modifying
    @Query("UPDATE CustomerOrder co " +
            "SET co.status = 'STARTED' " +
            "WHERE co = :customerOrder AND co.customer = :customer")
    void changeStatusOfCustomerOrderToStarted(@Param("customerOrder") CustomerOrder customerOrder, @Param("customer") Customer customer);

    @Modifying
    @Query("UPDATE CustomerOrder co " +
            "SET co.status = 'FINISHED' " +
            "WHERE co = :customerOrder AND co.customer = :customer")
    void changeStatusOfCustomerOrderToFinished(@Param("customerOrder") CustomerOrder customerOrder, @Param("customer") Customer customer);
}
