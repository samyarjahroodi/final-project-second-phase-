package finalproject.finalproject.model.duty;

import finalproject.finalproject.base.entity.BaseEntity;
import finalproject.finalproject.model.operation.CustomerOrder;
import finalproject.finalproject.model.user.Expert;
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

    private Integer price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Duty duty;


    @ManyToMany
    @JoinTable(name = "SubDuty_expert")
    private List<Expert> experts;

    @OneToMany(mappedBy = "duty")
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
