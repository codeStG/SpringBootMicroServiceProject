package com.stgcodes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stgcodes.validation.enums.PhoneType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PHONE_TBL")
@Getter
@Setter
@ToString
@NoArgsConstructor
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

    @JsonIgnore
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
