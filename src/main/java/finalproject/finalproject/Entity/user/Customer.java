package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.utility.Wallet;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer extends Person {
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> orders;

    @OneToOne
    private Wallet wallet;

}
