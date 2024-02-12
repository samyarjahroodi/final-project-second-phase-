package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SubDutyRepository
        extends JpaRepository<SubDuty, Integer> {

    @Query("UPDATE SubDuty s SET s.duty = null WHERE s = :subDuty")
    @Modifying
    void deleteSubDutyFromTheExistDuty(@Param("subDuty") SubDuty subDuty);

    boolean existsByName(String name);

}
