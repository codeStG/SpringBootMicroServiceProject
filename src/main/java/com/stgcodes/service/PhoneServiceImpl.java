package com.stgcodes.service;

import com.stgcodes.dao.PhoneDaoImpl;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneDaoImpl dao;
    
    @Override
    public List<Phone> getAllPhones() {
        List<PhoneEntity> phoneEntities = dao.findAll();
        List<Phone> phones = new ArrayList<>();

        for(PhoneEntity phoneEntity : phoneEntities) {
            phones.add(PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity));
        }

        return phones;
    }

    @Override
    public Phone getPhoneById(Long phoneId) {
        PhoneEntity phoneEntity = dao.findById(phoneId);
        Phone phone;

        if(phoneEntity == null) {
            log.info("ID " + phoneId + " does not exist");
            throw new IdNotFoundException();
        } else {
            phone = PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);
        }

        return phone;
    }

    @Override
    public Phone addPhone(Phone phone) {
        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
        return PhoneMapper.INSTANCE.phoneEntityToPhone(dao.save(phoneEntity));
    }

    @Override
    public void deletePhone(Long phoneId) {
        PhoneEntity phoneEntity = dao.findById(phoneId);
        dao.delete(phoneEntity);
    }
}
