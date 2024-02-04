package finalproject.finalproject.service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponseToChangePassword {

    private String username;

    private String password;
}
