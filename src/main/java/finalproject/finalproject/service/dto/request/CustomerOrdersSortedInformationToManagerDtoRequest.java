package finalproject.finalproject.service.dto.request;

import finalproject.finalproject.Entity.operation.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrdersSortedInformationToManagerDtoRequest {

    private LocalDate startedDuration;

    private LocalDate endedDuration;

    private Status status;

    private String subDutyName;

    private String dutyName;

}
