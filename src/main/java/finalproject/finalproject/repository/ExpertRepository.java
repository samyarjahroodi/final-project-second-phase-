package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.user.Expert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository
        extends PersonRepository<Expert> {
    @Query("SELECT AVG(c.star) " +
            "FROM Comment c " +
            "JOIN c.customerOrder o " +
            "JOIN o.suggestions s " +
            "WHERE s.expert = :expert AND s.isApproved = true")
    double averageStarOfExpert(@Param("expert") Expert expert);
}
