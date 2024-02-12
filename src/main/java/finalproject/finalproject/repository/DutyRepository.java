package finalproject.finalproject.repository;


import finalproject.finalproject.Entity.duty.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface DutyRepository
        extends JpaRepository<Duty, Integer> {

    boolean existsByName(String name);
}
