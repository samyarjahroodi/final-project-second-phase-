package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository<T extends Person>
        extends JpaRepository<T, Integer> {

}
