package finalproject.finalproject.service.dto.request;

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

    private String username;

    private String password;

    private String pathName;

}
