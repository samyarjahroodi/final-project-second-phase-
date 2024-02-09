package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDtoRequest {

    private int suggestedPrice;

    private LocalDate whenSuggestionCreated;

    private ZonedDateTime suggestedTimeToStartTheProject;

    private int hoursThatTaken;

}
