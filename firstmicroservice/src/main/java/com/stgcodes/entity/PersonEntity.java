package com.stgcodes.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    @NonNull
    private String lastName;

    @Column(name = "username", nullable = false, length = 25, unique = true)
    @NonNull
    private String username;

    @Column(name = "date_of_birth", nullable = false)
    @NonNull
    private String dateOfBirth;

    @Column(name = "ssn", nullable = false)
    @NonNull
    private String socialSecurityNumber;

    @Column(name = "gender")
    @NonNull
    private String gender;

    @Column(name = "email", nullable = false)
    @NonNull
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
