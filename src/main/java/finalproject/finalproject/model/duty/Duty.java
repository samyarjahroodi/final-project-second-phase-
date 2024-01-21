package finalproject.finalproject.model.duty;

import finalproject.finalproject.base.entity.BaseEntity;
import finalproject.finalproject.model.operation.CustomerOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@Entity

public class Duty extends BaseEntity<Integer> {
    private String name;

    @OneToMany(mappedBy = "duty")
    private List<SubDuty> subDuties;

    @OneToMany(mappedBy = "duty")
    private List<CustomerOrder> orders;

    @Override
    public String toString() {
        return "Duty{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", duties=" + subDuties +
                ", orders=" + orders +
                '}';
    }
}
