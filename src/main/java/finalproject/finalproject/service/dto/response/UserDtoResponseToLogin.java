package finalproject.finalproject.service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponseToLogin {

    private String username;

    private String newPassword;
}
