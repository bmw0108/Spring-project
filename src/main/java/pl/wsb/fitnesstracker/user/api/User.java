package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email
    ) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.birthdate = birthdate;
        this.email     = email;
    }

    /**
     * Updates this user’s mutable fields from another User instance.
     *
     * @param other the User whose data should be copied into this one
     */
    public void updateFrom(User other) {
        this.firstName = other.getFirstName();
        this.lastName  = other.getLastName();
        this.birthdate = other.getBirthdate();
        this.email     = other.getEmail();
    }
}
