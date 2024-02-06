package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.Card;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.Wallet;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
/*@AllArgsConstructor*/
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer extends Person {
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> orders;

    @OneToOne
    private Wallet wallet;

    @OneToMany(mappedBy = "customer")
    private List<Card> card;

    public Customer(List<CustomerOrder> orders, Wallet wallet, List<Card> card) {
        this.orders = orders;
        this.wallet = wallet;
        this.card = card;
    }
}
