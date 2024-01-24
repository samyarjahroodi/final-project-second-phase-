package finalproject.finalproject.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertDto extends UserDto {

    byte[] profileImage;

    String pathName;
}
