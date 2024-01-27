package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Query("UPDATE Expert e SET e.registrationStatus = :status WHERE e = :expert")
    void updateRegistrationStatusForSpecificExpert(@Param("status") RegistrationStatus status, @Param("expert") Expert expert);

    @Modifying
    @Query("DELETE FROM SubDuty s WHERE :expert MEMBER OF s.experts AND s = :subduty")
    void deleteSubDutyOFTheSpecificExpert(@Param("expert") Expert expert, @Param("subduty") SubDuty subDuty);


}
