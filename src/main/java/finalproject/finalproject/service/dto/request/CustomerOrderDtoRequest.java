package finalproject.finalproject.service.dto.request;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerOrderDtoRequest {
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @PositiveOrZero
    private double price;

    @NotNull(message = "Time of order cannot be null")
    @Future(message = "Time of order must be in the future")
    private LocalDate timeOfOrder;

    @NotBlank
    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Future(message = "Suggested time to start the project by customer must be in the future")
    private LocalDate suggestedTimeToStartTheProjectByCustomer;

    private Duty duty;

    private SubDuty subDuty;

}
