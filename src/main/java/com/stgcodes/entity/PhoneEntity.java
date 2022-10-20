package com.stgcodes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PHONE_TBL")
@Getter
@Setter
@ToString(exclude = {"personEntity"})
@NoArgsConstructor
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long phoneId;

    @NaturalId
    @Column(name = "phone_number", nullable = false, length = 12, unique = true)
    private String phoneNumber;

    @Column(name = "phone_type", nullable = false, length = 8)
    private String phoneType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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
