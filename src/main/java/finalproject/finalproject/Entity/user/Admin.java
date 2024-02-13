package finalproject.finalproject.Entity.user;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Admin extends Person {
    private Role role = Role.ROLE_ADMIN;

    private boolean isSuperAdmin;
}
