package finalproject.finalproject.Entity;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.user.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class Card extends BaseEntity<Integer> {

    String cardNumber;

    int cvv2;

    int password;

    @ManyToOne(cascade = CascadeType.ALL)
    Customer customer;

}
