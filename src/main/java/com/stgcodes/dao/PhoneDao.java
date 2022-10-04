package com.stgcodes.dao;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class PhoneDao extends Dao<PhoneEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    public List<PhoneEntity> findAll() {
        List<PhoneEntity> phoneEntities = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery("SELECT * FROM PHONE_TBL")
                .addEntity(PhoneEntity.class)
                .list().forEach(p -> phoneEntities.add((PhoneEntity) p));

        transaction.commit();

        return phoneEntities;
    }

    public Optional<PhoneEntity> findById(Long phoneId) {
        PhoneEntity phoneEntity;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        phoneEntity = (PhoneEntity) session.createSQLQuery("SELECT * FROM PHONE_TBL WHERE phone_id = " + phoneId)
                .addEntity(PhoneEntity.class).uniqueResult();

        session.getTransaction().commit();

        return Optional.ofNullable(phoneEntity);
    }

    @PostConstruct
    public void PostConstruct() {
        Phone phone = Phone.builder()
                .phoneNumber("1234567890")
                .phoneType("mobile")
                .build();

        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);

        log.info("TESTING PHONE SAMPLE DATA CRUD OPERATIONS IN DAO");
        this.save(phoneEntity);
        this.findAll().forEach(System.out::println);
        this.findById(2L);
        this.delete(phoneEntity);
        this.findAll().forEach(System.out::println);
        log.info("COMPLETED TESTING PHONE SAMPLE DATA CRUD OPERATIONS IN DAO");
    }
}
