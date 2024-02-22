package finalproject.finalproject.service.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportForManagerDtoResponse {

    private LocalDate creationDate;

    private Integer numberOfOrdersByCustomer;

    private Integer numberOfOrdersThatAreDoneByExpert;

}
