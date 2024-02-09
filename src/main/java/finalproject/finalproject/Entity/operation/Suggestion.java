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
import java.time.ZonedDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Suggestion extends BaseEntity<Integer> {
    @Column(name = "suggested_price")
    private int suggestedPrice;

    @Column(name = "when_suggestion_created")
    private LocalDate whenSuggestionCreated;

    @Column(name = "suuggested_time_to_start_the_project")
    private ZonedDateTime suggestedTimeToStartTheProject;

    @Column(name = "hours_that_taken")
    private int hoursThatTaken;

    @ManyToOne
    private CustomerOrder order;

    private Boolean isApproved;

    @ManyToOne
    @JsonIgnore
    private Expert expert;

}
