package finalproject.finalproject.Entity.duty;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.operation.CustomerOrder;
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
