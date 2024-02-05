package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "expert")
public class Expert extends Person {
    @Column(name = "registration_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus = RegistrationStatus.AWAITING_CONFIRMATION;

    //when expert is registered!!
    @Column(name = "when_expert_registered", nullable = false)
    private LocalDate whenExpertRegistered;

    //image of the expert!!
    @Column(name = "image_of_expert", nullable = false, columnDefinition = "bytea")
    private byte[] image;

    @Max(5)
    @Min(0)
    private Double star;

    @OneToMany(mappedBy = "expert")
    private List<Suggestion> suggestions;

    @OneToOne
    private Wallet wallet;

}
