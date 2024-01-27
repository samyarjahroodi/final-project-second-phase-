package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DutyRepository
        extends JpaRepository<Duty, Integer> {
    @Query(value = "SELECT sd FROM SubDuty sd WHERE sd.duty = :duty")
    List<SubDuty> showSubDutiesOfSpecificDuty(Duty duty);



}
