package finalproject.finalproject.service.dto;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Expert;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDto {

    private int suggestedPrice;

    private LocalDate whenSuggestionCreated;

    private LocalDate suggestedTimeToStartTheProject;

    private int daysThatTaken;

    /*private CustomerOrder order;*/

    private Boolean isApproved;

   /* private Expert expert;*/

}
