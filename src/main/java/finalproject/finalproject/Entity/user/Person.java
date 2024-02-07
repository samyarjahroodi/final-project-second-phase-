package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@Entity
public class Person extends BaseEntity<Integer> {

    private String firstname;


    private String lastname;


    private String email;


    private String password;


    private String username;

}
