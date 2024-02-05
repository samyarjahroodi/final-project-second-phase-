package finalproject.finalproject.Entity.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import finalproject.finalproject.Entity.user.Expert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
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
    @JsonIgnore
    private Expert expert;

}
