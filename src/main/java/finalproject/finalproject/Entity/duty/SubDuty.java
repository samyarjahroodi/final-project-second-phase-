package finalproject.finalproject.Entity.duty;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Expert;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@ToString
public class SubDuty extends BaseEntity<Integer> {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(/*nullable = false*/)
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
