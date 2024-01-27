package finalproject.finalproject.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertDto {

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String password;

    byte[] profileImage;

    String pathName;
}
