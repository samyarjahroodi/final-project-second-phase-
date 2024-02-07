package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DutyDtoRequest {
    @NotNull
    private String name;

}
