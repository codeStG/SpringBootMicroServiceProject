package com.stgcodes.dao;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("addressDao")
public class AddressDaoImpl extends DaoImpl<AddressEntity> implements AddressDao {

//    @PostConstruct
//    public void PostConstruct() {
//        Address address = Address.builder()
//                .lineOne("1234 Vegas Blvd")
//                .lineTwo("Unit 2")
//                .city("Las Vegas")
//                .state("Nevada")
//                .zip("123456789")
//                .build();
//
//        AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(address);
//
//        System.out.println("In Post Construct");
//        save(addressEntity);
//        findAll().forEach(System.out::println);
//        findById(4L);
//        delete(addressEntity);
//        findAll().forEach(System.out::println);
//        System.out.println("After for each");
//    }
}