package com.stgcodes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ADDRESS_TBL")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "line_one", nullable = false, length = 75)
    private String lineOne;

    @Column(name = "line_two", length = 25)
    private String lineTwo;

    @Column(name = "city", nullable = false, length = 75)
    private String city;

    @Column(name = "state", nullable = false, length = 20)
    private String state;

    @Column(name = "zip", nullable = false, length = 10)
    private String zip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AddressEntity that = (AddressEntity) o;
        return addressId != null && Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
