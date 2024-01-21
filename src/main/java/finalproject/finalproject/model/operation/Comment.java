package finalproject.finalproject.model.operation;

import finalproject.finalproject.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends BaseEntity<Integer> {
    private String comment;

    @Max(5)
    @Min(0)
    private Double star;

    @OneToOne
    private CustomerOrder customerOrder;
}
