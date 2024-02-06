package finalproject.finalproject.service.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class CommentDtoResponse {
    private String comment;

    @Max(5)
    @Min(0)
    private Double star;

}
