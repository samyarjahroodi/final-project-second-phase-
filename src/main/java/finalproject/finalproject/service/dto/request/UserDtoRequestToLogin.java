package finalproject.finalproject.service.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoRequestToLogin {

    private String username;

    private String password;
}
