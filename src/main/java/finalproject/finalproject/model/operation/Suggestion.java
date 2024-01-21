package finalproject.finalproject.model.operation;

import finalproject.finalproject.base.entity.BaseEntity;
import finalproject.finalproject.model.user.Expert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Suggestion extends BaseEntity<Integer> {
    @Column(name = "suggested_price", nullable = false)
    private int suggestedPrice;

    @Column(name = "when_suggestion_created", nullable = false)
    private LocalDate whenSuggestionCreated;

    @Column(name = "suuggested_time_to_start_the_project", nullable = false)
    private LocalDate suggestedTimeToStartTheProject;

    @Column(name = "days_that_taken", nullable = false)
    private int daysThatTaken;

    @ManyToOne
    private CustomerOrder order;

    private Boolean isApproved;

    @ManyToOne
    private Expert expert;

}
