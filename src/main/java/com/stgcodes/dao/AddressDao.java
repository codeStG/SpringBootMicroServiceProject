package com.stgcodes.dao;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AddressDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<AddressEntity> getAddresses() {
        Query query = sessionFactory
                .getCurrentSession()
                .createSQLQuery("SELECT * FROM ADDRESS_TBL")
                .addEntity(AddressEntity.class);

        return query.list();
    }

    public AddressEntity getAddressById(Long addressId) {
        Query query = sessionFactory
                .getCurrentSession()
                .createSQLQuery("SELECT * FROM ADDRESS_TBL WHERE address_id = " + addressId)
                .addEntity(AddressEntity.class);

        return (AddressEntity) query.uniqueResult();
    }

    public AddressEntity addAddress(AddressEntity addressEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.persist(addressEntity);

        session.getTransaction().commit();
        session.close();

        return addressEntity;
    }

    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(addressEntity);
        session.getTransaction().commit();

        session.close();

        return addressEntity;
    }

    @PostConstruct
    public void PostConstruct() {
        Address address = Address.builder()
                .lineOne("1234 Vegas Blvd")
                .lineTwo("Unit 2")
                .city("Las Vegas")
                .state("Nevada")
                .zip("123456789")
                .build();

        AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(address);

        System.out.println("In Post Construct");
        this.addAddress(addressEntity);
        this.getAddresses().forEach(System.out::println);
        this.getAddressById(4L);
        this.deleteAddress(addressEntity);
        this.getAddresses().forEach(System.out::println);
        System.out.println("After for each");
    }
}
