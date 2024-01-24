package finalproject.finalproject.service.dto;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.user.Expert;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubDutyDto {

    private String name;

    private String description;

    private Integer price;

    private Duty duty;

    private List<Expert> experts;

}
