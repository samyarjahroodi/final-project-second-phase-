package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoRequestToLogin {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
