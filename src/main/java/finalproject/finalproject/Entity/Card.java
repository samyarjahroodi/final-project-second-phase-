package finalproject.finalproject.Entity;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.user.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class Card extends BaseEntity<Integer> {

    @NotNull
    @Size(min = 16, max = 16)
    String cardNumber;

    @Min(100)
    @Max(9999)
    int cvv2;

    @Min(1000)
    @Max(9999)
    int password;

    int money = 50;

    LocalDate expireDate;

    @ManyToOne(cascade = CascadeType.ALL)
    Customer customer;

}
