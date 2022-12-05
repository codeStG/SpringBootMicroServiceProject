package com.stgcodes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stgcodes.dao.UserDao;
import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.PhoneValidator;

@Component("phoneService")
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneDao dao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PhoneValidator validator;

    @Override
    public List<Phone> findAll() {
        List<Phone> phones = new ArrayList<>();
        dao.findAll().forEach(e -> phones.add(mapToModel(e)));

        return phones;
    }

    @Override
    public Phone findById(Long phoneId) {
        PhoneEntity phoneEntity = dao.findById(phoneId);

        return mapToModel(phoneEntity);
    }

    @Override
    public Phone save(Phone phone, Long userId) {
        UserEntity userEntity = userDao.findById(userId);
        validator.validate(phone);

        PhoneEntity phoneEntity = mapToEntity(phone);
        userEntity.addPhone(phoneEntity);
        PhoneEntity result = dao.save(phoneEntity);

        return mapToModel(result);
    }

    @Override
    public Phone update(Phone phone, Long phoneId) {
        UserEntity userEntity = dao.findById(phoneId).getUserEntity();
        validator.validate(phone);

        PhoneEntity phoneEntity = mapToEntity(phone);
        phoneEntity.setPhoneId(phoneId);
        phoneEntity.setUserEntity(userEntity);

        return mapToModel(dao.update(phoneEntity));
    }

    @Override
    public void delete(Long phoneId) {
    	PhoneEntity phoneEntity = dao.findById(phoneId);
    	UserEntity userEntity = phoneEntity.getUserEntity();
    	
    	validator.validateUserHasMorePhones(userEntity);
    
        userEntity.removePhone(phoneEntity);
        userDao.update(userEntity);
    }

    private PhoneEntity mapToEntity(Phone phone) {
        return PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
    }

    private Phone mapToModel(PhoneEntity phoneEntity) {
        return PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);
    }
}
