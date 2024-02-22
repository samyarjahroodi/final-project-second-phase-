package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PersonRepository<T extends Person>
        extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

    @Query("SELECT p FROM Person p WHERE p.username = :username")
    Optional<T> findByUsernameIfItsExist(String username);

    T findByUsername(String username);

    T findByVerificationCode(String verificationCode);

    boolean existsByUsername(String username);

}
