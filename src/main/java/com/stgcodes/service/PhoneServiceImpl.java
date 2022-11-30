package com.stgcodes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.PhoneValidator;

@Component("phoneService")
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneDao dao;

    @Autowired
    PersonDao personDao;

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
    public Phone save(Phone phone, Long personId) {
        PersonEntity personEntity = personDao.findById(personId);
        validator.validate(phone);

        PhoneEntity phoneEntity = mapToEntity(phone);
        personEntity.addPhone(phoneEntity);
        PhoneEntity result = dao.save(phoneEntity);

        return mapToModel(result);
    }

    @Override
    public Phone update(Phone phone, Long phoneId) {
        PersonEntity personEntity = dao.findById(phoneId).getPersonEntity();
        validator.validate(phone);

        PhoneEntity phoneEntity = mapToEntity(phone);
        phoneEntity.setPhoneId(phoneId);
        phoneEntity.setPersonEntity(personEntity);

        return mapToModel(dao.update(phoneEntity));
    }

    @Override
    public void delete(Long phoneId) {
    	PhoneEntity phoneEntity = dao.findById(phoneId);
    	PersonEntity personEntity = phoneEntity.getPersonEntity();
    	
    	validator.validateUserHasMorePhones(personEntity);
    
        personEntity.removePhone(phoneEntity);
        personDao.update(personEntity);
    }

    private PhoneEntity mapToEntity(Phone phone) {
        return PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
    }

    private Phone mapToModel(PhoneEntity phoneEntity) {
        return PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);
    }
}
