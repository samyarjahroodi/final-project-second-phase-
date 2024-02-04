package finalproject.finalproject.service.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubDutyDtoRequest {

    private String name;

    private String description;

    private Integer price;

   /* private Duty duty;

    private List<Expert> experts;*/

}
