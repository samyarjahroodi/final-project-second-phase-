package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository
        extends JpaRepository<Suggestion, Integer> {
    @Query("SELECT sd FROM SubDuty sd JOIN sd.experts e WHERE e = :expert")
    List<SubDuty> giveSubDutiesOfExpert(@Param("expert") Expert expert);


/*    @Modifying
    @Query("UPDATE Suggestion s " +
            "SET s.isApproved = true, s.order.status = 'WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE' " +
            "WHERE s = :suggestion AND s.order.customer = :customer")
    void approveSuggestion(@Param("suggestion") Suggestion suggestion, @Param("customer") Customer customer);*/

    @Modifying
    @Query("UPDATE CustomerOrder o " +
            "SET o.status = 'WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE' " +
            "WHERE o = :customerOrder AND o.customer = :customer")
    void approveSuggestion(@Param("customerOrder") CustomerOrder customerOrder, @Param("customer") Customer customer);
}
