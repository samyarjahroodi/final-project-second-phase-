package finalproject.finalproject.service.dto.request;

import finalproject.finalproject.Entity.user.RegistrationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertDtoRequest {
    private String firstname;

    private String lastname;

    private String email;

    private RegistrationStatus registrationStatus = RegistrationStatus.AWAITING_CONFIRMATION;

    private String username;

    private String password;

    private String pathName;

    private String fieldOfEndeavor;
}
