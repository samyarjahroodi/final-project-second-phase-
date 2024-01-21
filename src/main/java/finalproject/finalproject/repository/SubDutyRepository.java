package finalproject.finalproject.repository;


import finalproject.finalproject.model.duty.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubDutyRepository extends JpaRepository<SubDuty, Integer> {
}
