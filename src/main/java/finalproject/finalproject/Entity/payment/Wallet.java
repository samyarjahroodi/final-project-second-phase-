package finalproject.finalproject.Entity.payment;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet extends BaseEntity<Integer> {
    private double creditOfWallet;

    @Builder.Default
    private boolean isActive = true;

}
