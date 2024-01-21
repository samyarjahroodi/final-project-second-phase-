package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyRepository
        extends JpaRepository<Duty, Integer> {
    @Query(value = "SELECT sd FROM SubDuty sd WHERE sd.duty.id = :dutyId")
    List<SubDuty> showSubDutiesOfSpecificDuty(@Param("dutyId") Integer id);
}
