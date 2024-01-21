package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubDutyRepository
        extends JpaRepository<SubDuty, Integer> {
}
