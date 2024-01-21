package finalproject.finalproject.model.utility;

import finalproject.finalproject.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet extends BaseEntity<Integer> {
    private int creditOfWallet;

}
