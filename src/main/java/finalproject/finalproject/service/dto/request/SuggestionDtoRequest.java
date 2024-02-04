package finalproject.finalproject.service.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDtoRequest {

    private int suggestedPrice;

    private LocalDate whenSuggestionCreated;

    private LocalDate suggestedTimeToStartTheProject;

    private int daysThatTaken;

    /*private CustomerOrder order;*/

   /* private Boolean isApproved;*/

   /* private Expert expert;*/

}
