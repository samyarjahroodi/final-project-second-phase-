package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.utility.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Expert extends Person {
    @Column(name = "registration_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus;

    //when expert is registered!!
    @Column(name = "when_expert_registered", nullable = false)
    private LocalDate whenExpertRegistered;

    //image of the expert!!
    @Lob
    @Column(name = "image_of_expert", nullable = false)
    //@Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    @Max(5)
    @Min(0)
    private Double star;

    @OneToMany(mappedBy = "expert")
    private List<Suggestion> suggestions;

    @OneToOne
    private Wallet wallet;


}
