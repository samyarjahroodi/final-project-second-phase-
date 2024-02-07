package finalproject.finalproject.repository;

import finalproject.finalproject.Entity.payment.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository
        extends JpaRepository<Wallet, Integer> {

}
