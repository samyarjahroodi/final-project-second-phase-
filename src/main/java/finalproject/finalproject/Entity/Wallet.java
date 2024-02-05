package finalproject.finalproject.Entity;

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
    private int creditOfWallet;

}
