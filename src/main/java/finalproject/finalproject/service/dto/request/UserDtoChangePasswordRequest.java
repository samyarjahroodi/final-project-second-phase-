package finalproject.finalproject.service.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoChangePasswordRequest {
    private String username;

    private String oldPassword;

    private String newPassword;
}
