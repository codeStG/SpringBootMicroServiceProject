package com.stgcodes.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PERSON_TBL")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;

    @Column(name = "FIRST_NAME", nullable = false, length = 25)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 25)
    private String lastName;

    @Column(name = "USERNAME", nullable = false, length = 25, unique = true)
    private String username;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private Date dateOfBirth;

    @Column(name = "SSN", nullable = false)
    private String socialSecurityNumber;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "EMAIL", nullable = false)
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
