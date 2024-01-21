package finalproject.finalproject.model.user;

import jakarta.persistence.Entity;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Admin extends Person {
    private boolean isSuperAdmin;
}
