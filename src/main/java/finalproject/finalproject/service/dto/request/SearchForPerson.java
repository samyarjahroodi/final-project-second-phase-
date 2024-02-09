package finalproject.finalproject.service.dto.request;

import finalproject.finalproject.Entity.user.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SearchForPerson {

    String firstname;

    String lastname;

    String email;

    String username;

    Integer star;

    String subDuty;

    Role role;

}