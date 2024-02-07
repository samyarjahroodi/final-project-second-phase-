package finalproject.finalproject.service.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoRequest {
    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @UniqueElements
    @Email
    private String email;

    @NotBlank
    @UniqueElements
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    private String password;

}
