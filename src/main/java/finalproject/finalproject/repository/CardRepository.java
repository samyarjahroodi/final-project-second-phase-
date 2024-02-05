package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository
        extends JpaRepository<Card, Integer> {

}
