package finalproject.finalproject.Entity.operation;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends BaseEntity<Integer> {
    private String comment;

    private Double star;

    @OneToOne
    private CustomerOrder customerOrder;
}
