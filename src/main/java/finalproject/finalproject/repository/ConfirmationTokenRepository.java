package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.operation.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

}