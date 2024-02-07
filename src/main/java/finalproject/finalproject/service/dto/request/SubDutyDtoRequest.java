package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubDutyDtoRequest {
    @NotNull
    private String name;

    @NotNull
    private String description;

    private Integer price;


}
