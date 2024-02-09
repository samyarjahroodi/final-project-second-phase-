package finalproject.finalproject.service.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDtoResponse {

    private int suggestedPrice;

    private LocalDate whenSuggestionCreated;

    private ZonedDateTime suggestedTimeToStartTheProject;

    private int hoursThatTaken;


}
