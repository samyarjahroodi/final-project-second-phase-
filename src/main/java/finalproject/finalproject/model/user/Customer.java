package finalproject.finalproject.model.user;

import finalproject.finalproject.model.operation.CustomerOrder;
import finalproject.finalproject.model.utility.Wallet;
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
