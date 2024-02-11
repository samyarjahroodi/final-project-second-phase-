package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Expert;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ExpertRepository
        extends PersonRepository<Expert> {
    @Query("SELECT AVG(c.star) " +
            "FROM Comment c " +
            "JOIN c.customerOrder o " +
            "JOIN o.suggestions s " +
            "WHERE s.expert = :expert AND s.isApproved = true")
    double averageStarOfExpert(@Param("expert") Expert expert);

    @Query("SELECT c.star FROM Comment c WHERE c.customerOrder = :customerOrder")
    double seeStarOfOrder(CustomerOrder customerOrder);

}
