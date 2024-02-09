package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.payment.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository
        extends JpaRepository<Card, Integer> {

    @Query("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber AND c.cvv2 = :cvv2 AND c.password = :password")
    Optional<Card> findCardByCardNumberAndCvv2AndPassword(@Param("cardNumber") String cardNumber,
                                                          @Param("cvv2") int cvv2,
                                                          @Param("password") int password);


}
