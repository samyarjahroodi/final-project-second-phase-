package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Integer> {

    @Query("SELECT co FROM CustomerOrder co " +
            "WHERE :expert MEMBER OF co.subDuty.experts " +
            "AND (co.status = 'WAITING_FOR_THE_SUGGESTION_OF_EXPERTS' OR co.status = 'WAITING_EXPERT_SELECTION')")
    List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(@Param("expert") Expert expert);

    @Query("SELECT s FROM Suggestion s " +
            "JOIN FETCH s.order co " +
            "WHERE co.customer = :customer " +
            "ORDER BY s.suggestedPrice ASC")
    List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(@Param("customer") Customer customer);

    @Query("SELECT s FROM Suggestion s WHERE s.order.customer= :customer ORDER BY s.expert.star DESC ")
    List<Suggestion> showSuggestionsBasedOnStarOfExpert(@Param("customer") Customer customer);

    @Query("SELECT co FROM CustomerOrder co WHERE co.status = :status AND co.customer = :customer")
    List<CustomerOrder> seeCustomerOrderByStatus(@Param("customer") Customer customer, @Param("status") Status status);


}
