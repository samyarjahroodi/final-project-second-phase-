package finalproject.finalproject.service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardDtoRequest {

    String cardNumber;

    int month;

    int year;

    int cvv2;

    int password;

}
