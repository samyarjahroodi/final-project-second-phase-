package finalproject.finalproject.Entity.duty;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Expert;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@ToString
public class SubDuty extends BaseEntity<Integer> {

    private String name;

    private String description;

    private double price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Duty duty;


    @ManyToMany
    @JoinTable(name = "SubDuty_expert")
    private List<Expert> experts;

    @OneToMany(mappedBy = "subDuty")
    private List<CustomerOrder> orders;

    @Override
    public String toString() {
        return "SubDuty{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", experts=" + experts +
                '}';
    }

}
