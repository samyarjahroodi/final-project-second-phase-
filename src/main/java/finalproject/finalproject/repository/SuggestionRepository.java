package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository
        extends JpaRepository<Suggestion, Integer> {
    @Query("SELECT sd FROM SubDuty sd JOIN sd.experts e WHERE e = :expert")
    List<SubDuty> giveSubDutiesOfExpert(@Param("expert") Expert expert);
}
