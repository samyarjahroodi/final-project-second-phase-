package finalproject.finalproject.service.dto.response;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerOrderDtoResponse {

    private String description;

    private double price;

    private LocalDate timeOfOrder;

    private String address;

    private Status status;

    private LocalDate suggestedTimeToStartTheProjectByCustomer;

}
