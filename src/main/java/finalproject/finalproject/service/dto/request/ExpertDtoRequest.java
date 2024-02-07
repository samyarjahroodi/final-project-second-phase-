package finalproject.finalproject.service.dto.request;

import finalproject.finalproject.Entity.user.RegistrationStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertDtoRequest {
    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @UniqueElements
    @Email
    private String email;

    private RegistrationStatus registrationStatus = RegistrationStatus.AWAITING_CONFIRMATION;

    @NotNull
    @UniqueElements
    private String username;

    @NotNull
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    private String password;

    @NotNull
    private String pathName;

}
