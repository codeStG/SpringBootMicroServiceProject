package com.stgcodes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PERSON_TBL")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @Column(name = "username", nullable = false, length = 25, unique = true)
    private String username;

    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "ssn", nullable = false, unique = true)
    private String socialSecurityNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy="personEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneEntity> phones = new ArrayList<>();

    public void addPhone(PhoneEntity phone) {
        phone.setPersonEntity(this);
        phones.add(phone);
    }

    public void removePhone(PhoneEntity phone) {
        phones.remove(phone);
    }

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
