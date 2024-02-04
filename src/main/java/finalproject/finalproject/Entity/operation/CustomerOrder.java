package finalproject.finalproject.Entity.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Customer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_order")
public class CustomerOrder extends BaseEntity<Integer> {
    private String description;

    private double price;

    //time of order!!!
    @Column(name = "time_of_order")
    private LocalDate timeOfOrder;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "start_of_the_project")
    private LocalDate suggestedTimeToStartTheProjectByCustomer;

    @ManyToOne
    @JsonIgnore
    private Duty duty;

    @ManyToOne
    @JsonIgnore
    private SubDuty subDuty;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<Suggestion> suggestions;

}