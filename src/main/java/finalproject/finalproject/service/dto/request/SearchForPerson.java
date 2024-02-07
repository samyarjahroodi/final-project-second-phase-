package finalproject.finalproject.service.dto.request;

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

}