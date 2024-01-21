package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.operation.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository
        extends JpaRepository<Suggestion, Integer> {

}
