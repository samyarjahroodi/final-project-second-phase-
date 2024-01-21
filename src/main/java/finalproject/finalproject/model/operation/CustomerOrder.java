package finalproject.finalproject.model.operation;

import finalproject.finalproject.base.entity.BaseEntity;
import finalproject.finalproject.model.duty.Duty;
import finalproject.finalproject.model.duty.SubDuty;
import finalproject.finalproject.model.user.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_order")
public class CustomerOrder extends BaseEntity<Integer> {
    private String description;

    private int price;

    //time of order!!!
    @Column(name = "time_of_order")
    private LocalDate timeOfOrder;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "start_of_the_project")
    private LocalDate starOfTheProject;

    @ManyToOne
    private Duty duty;

    @ManyToOne
    private SubDuty subDuty;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Suggestion> suggestions;


}