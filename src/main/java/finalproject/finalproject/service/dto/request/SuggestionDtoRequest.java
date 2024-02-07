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

    @NotNull(message = "Suggestion creation date cannot be null")
    @PastOrPresent(message = "Suggestion creation date must be in the past or present")
    private LocalDate whenSuggestionCreated;

    @NotNull(message = "Suggested time to start the project cannot be null")
    @Future(message = "Suggested time to start the project must be in the future")
    private LocalDate suggestedTimeToStartTheProject;

    @NotNull(message = "days that taken cannot be null")
    @Min(value = 0, message = "Days taken must be a non-negative number")
    private int daysThatTaken;
}
