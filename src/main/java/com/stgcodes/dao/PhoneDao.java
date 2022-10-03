package com.stgcodes.dao;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class PhoneDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<PhoneEntity> getPhones() {
        Query query = sessionFactory
                .openSession()
                .createSQLQuery("SELECT * FROM PHONE_TBL")
                .addEntity(PhoneEntity.class);

        return query.list();
    }

    public PhoneEntity getPhoneById(Long phoneId) {
        Query query = sessionFactory
                .openSession()
                .createSQLQuery("SELECT * FROM PHONE_TBL WHERE phone_id = " + phoneId)
                .addEntity(PhoneEntity.class);

        return (PhoneEntity) query.uniqueResult();
    }

    public PhoneEntity addPhone(PhoneEntity phoneEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(phoneEntity);

        session.getTransaction().commit();
        session.close();

        return phoneEntity;
    }

    public PhoneEntity deletePhone(PhoneEntity phoneEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(phoneEntity);
        session.getTransaction().commit();

        session.close();

        return phoneEntity;
    }

    @PostConstruct
    public void PostConstruct() {
        Phone phone = Phone.builder()
                .phoneNumber("1234567890")
                .phoneType("mobile")
                .build();

        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);

        log.info("TESTING PHONE SAMPLE DATA CRUD OPERATIONS IN DAO");
        this.addPhone(phoneEntity);
        this.getPhones().forEach(System.out::println);
        this.getPhoneById(4L);
        this.deletePhone(phoneEntity);
        this.getPhones().forEach(System.out::println);
        log.info("COMPLETED TESTING PHONE SAMPLE DATA CRUD OPERATIONS IN DAO");
    }
}
