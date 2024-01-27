package finalproject.finalproject.Entity.duty;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Duty extends BaseEntity<Integer> {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "duty", fetch = FetchType.EAGER)
    private List<SubDuty> subDuties;

    @OneToMany(mappedBy = "duty", fetch = FetchType.EAGER)
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
