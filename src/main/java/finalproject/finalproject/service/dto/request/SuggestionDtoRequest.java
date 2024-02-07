package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionDtoRequest {
    @Min(value = 0, message = "Suggested price must be a positive number")
    private int suggestedPrice;

    private LocalDate whenSuggestionCreated=LocalDate.now();

    @Future(message = "Suggested time to start the project must be in the future")
    private LocalDate suggestedTimeToStartTheProject;

    @Min(value = 0, message = "Days taken must be a non-negative number")
    private int daysThatTaken;
}
