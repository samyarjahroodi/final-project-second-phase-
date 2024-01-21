package finalproject.finalproject.Entity.user;

import finalproject.finalproject.Entity.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass

//@Builder
@SuppressWarnings("unused")
public class Person extends BaseEntity<Integer> {
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;
}
