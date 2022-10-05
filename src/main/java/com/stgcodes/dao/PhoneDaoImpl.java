package com.stgcodes.dao;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("phoneDao")
@Slf4j
public class PhoneDaoImpl extends DaoImpl<PhoneEntity> implements PhoneDao {

//    @PostConstruct
//    public void PostConstruct() {
//        Phone phone = Phone.builder()
//                .phoneNumber("1234567890")
//                .phoneType("mobile")
//                .build();
//
//        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
//
//        log.info("TESTING PHONE SAMPLE DATA CRUD OPERATIONS IN DAO");
//
//        this.findById(1L);
//        this.save(phoneEntity);
//        this.findAll().forEach(System.out::println);
//        this.findById(2L);
//        this.delete(phoneEntity);
//        this.findAll().forEach(System.out::println);
//        log.info("COMPLETED TESTING PHONE SAMPLE DATA CRUD OPERATIONS IN DAO");
//    }
}
