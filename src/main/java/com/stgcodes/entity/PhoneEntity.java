package com.stgcodes.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stgcodes.validation.enums.PhoneType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PHONE_TBL")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"phoneId", "personEntity"})
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long phoneId;

    @NaturalId(mutable = true)
    @Column(name = "phone_number", nullable = false, length = 12, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type", nullable = false)
    private PhoneType phoneType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PhoneEntity phoneEntity = (PhoneEntity) o;
        return phoneId != null && Objects.equals(phoneNumber, phoneEntity.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash( phoneNumber );
    }
}
