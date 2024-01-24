package finalproject.finalproject.service.dto;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderDto {

    private String description;

    private int price;

    private LocalDate timeOfOrder;

    private String address;

    private Status status;

    private LocalDate starOfTheProject;

    private Duty duty;

    private SubDuty subDuty;

}
