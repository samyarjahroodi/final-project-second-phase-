package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.payment.Wallet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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

    @Column(name = "when_customer_registered")
    private LocalDate whenCustomerRegistered;

    @OneToMany(mappedBy = "customer")
    private List<Card> card;

    public Customer(List<CustomerOrder> orders, List<Card> card) {
        this.orders = orders;
        this.card = card;
    }
}
