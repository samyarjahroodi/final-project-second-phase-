package finalproject.finalproject.service.dto;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerOrderDto {

    private String description;

    private double price;

    private LocalDate timeOfOrder;

    private String address;

    private Status status;

    private LocalDate suggestedTimeToStartTheProjectByCustomer;

    private Duty duty;

    private SubDuty subDuty;

}
