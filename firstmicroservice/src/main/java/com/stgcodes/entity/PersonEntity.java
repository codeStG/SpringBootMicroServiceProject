package com.stgcodes.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "PERSON_TBL")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name", nullable = false, length = 25)
    @NonNull
    @NotBlank(message = "First Name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    @NonNull
    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Column(name = "username", nullable = false, length = 25, unique = true)
    @NonNull
    @NotBlank(message = "username is required")
    private String username;

    @Column(name = "date_of_birth", nullable = false)
    @NonNull
    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;

    @Column(name = "ssn", nullable = false)
    @NonNull
    @NotBlank(message = "Social Security Number is required")
    private String socialSecurityNumber;

    @Column(name = "gender")
    @NonNull
    @NotBlank(message = "Gender is required")
    private String gender;

    @Column(name = "email", nullable = false)
    @NonNull
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PersonEntity that = (PersonEntity) o;
        return personId != null && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
