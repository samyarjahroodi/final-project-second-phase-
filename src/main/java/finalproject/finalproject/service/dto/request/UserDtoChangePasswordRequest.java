package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoChangePasswordRequest {
    @NotNull
    private String username;

    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}
