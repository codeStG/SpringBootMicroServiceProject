package com.stgcodes.service;

import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneDao dao;
    
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
        Optional<PhoneEntity> phoneEntity = dao.findById(phoneId);
        Phone phone = null;

        if(phoneEntity.isEmpty()) {
            log.info("ID " + phoneId + " does not exist");
            throw new IdNotFoundException();
        } else {
            phone = PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity.get());
        }

        return phone;
    }

    @Override
    public void addPhone(Phone phone) {
        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
        dao.save(phoneEntity);
    }

    @Override
    public void deletePhone(Long phoneId) {
        Optional<PhoneEntity> phoneEntity = dao.findById(phoneId);
        dao.delete(phoneEntity.get());
    }
}
