package finalproject.finalproject.Entity.operation;

import finalproject.finalproject.Entity.user.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer tokenId;

    String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;

    @OneToOne
    Person person;

    public ConfirmationToken(Person person) {
        this.person = person;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}