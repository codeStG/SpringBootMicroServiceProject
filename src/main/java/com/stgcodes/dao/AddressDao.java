package com.stgcodes.dao;

import com.stgcodes.entity.AddressEntity;
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
                .openSession()
                .createSQLQuery("SELECT * FROM ADDRESS_TBL")
                .addEntity(AddressEntity.class);

        return query.list();
    }

    public AddressEntity getAddressById(Long addressId) {
        Query query = sessionFactory
                .openSession()
                .createSQLQuery("SELECT * FROM ADDRESS_TBL WHERE address_id = " + addressId)
                .addEntity(AddressEntity.class);

        return (AddressEntity) query.uniqueResult();
    }

    public AddressEntity addAddress(AddressEntity addressEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(addressEntity);

        session.getTransaction().commit();
        session.close();

        return addressEntity;
    }

    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(addressEntity);
        session.getTransaction().commit();

        session.close();

        return addressEntity;
    }

    @PostConstruct
    public void PostConstruct() {
        AddressEntity addressEntity = new AddressEntity("1234 Vegas Blvd", "Unit 2", "Las Vegas", "Nevada", "123456789");

        System.out.println("In Post Construct");
        this.addAddress(addressEntity);
        this.getAddresses().forEach(System.out::println);
        this.getAddressById(4L);
        this.deleteAddress(addressEntity);
        this.getAddresses().forEach(System.out::println);
        System.out.println("After for each");
    }
}