package com.stgcodes.entity;

import lombok.*;
import org.hibernate.Hibernate;

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

    @Column(name = "phone_number", nullable = false, length = 12, unique = true)
    private String phoneNumber;

    @Column(name = "phone_type", nullable = false, length = 8)
    private String phoneType;

//    @ManyToOne
//    @JoinColumn(name="person_fk")
//    private PersonEntity personEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PhoneEntity that = (PhoneEntity) o;
        return phoneId != null && Objects.equals(phoneId, that.phoneId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
