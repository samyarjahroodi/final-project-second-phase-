package finalproject.finalproject.service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class CardDtoRequest {
    @NotNull
    @Size(min = 16, max = 16)
    String cardNumber;

    @Min(1)
    @Max(12)
    int month;

    @Min(0)
    @Max(99)
    int year;

    @Min(100)
    @Max(9999)
    int cvv2;

    @Min(1000)
    @Max(9999)
    int password;

}
