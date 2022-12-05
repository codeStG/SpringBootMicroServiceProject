package com.stgcodes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.stgcodes.validation.enums.Gender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER_TBL")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties({"firstName", "lastName", "username", "dateOfBirth", "age", "socialSecurityNumber", "gender", "email", "phones"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @Column(name = "username", nullable = false, length = 25, unique = true)
    private String username;

    @JsonDeserialize(using= LocalDateDeserializer.class)
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private int age;

    @Column(name = "ssn", nullable = false, unique = true)
    private String socialSecurityNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy="userEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneEntity> phones = new ArrayList<>();

    public String getFullName() {
    	return firstName + " " + lastName;
    }
    
    public void addPhone(PhoneEntity phone) {
        phone.setUserEntity(this);
        phones.add(phone);
    }

    public void removePhone(PhoneEntity phone) {
        phones.remove(phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
