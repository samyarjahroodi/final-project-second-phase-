package finalproject.finalproject.Entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.payment.Wallet;
import jakarta.persistence.*;
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
    @Column(name = "registration_status")
    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus;

    //when expert is registered!!
    @Column(name = "when_expert_registered")
    private LocalDate whenExpertRegistered;

    //image of the expert!!
    @Column(name = "image_of_expert", columnDefinition = "bytea")
    @JsonIgnore
    private byte[] image;

    private Double star;

    @OneToMany(mappedBy = "expert")
    private List<Suggestion> suggestions;

    private String fieldOfEndeavor;

    private Role role;
}
