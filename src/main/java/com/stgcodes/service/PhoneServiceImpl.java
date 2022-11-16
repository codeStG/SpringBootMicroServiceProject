package com.stgcodes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.IllegalPhoneDeletionException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.PhoneValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        isValidRequestBody(phone);

        PhoneEntity phoneEntity = mapToEntity(phone);
        personEntity.addPhone(phoneEntity);
        PhoneEntity result = dao.save(phoneEntity);

        return mapToModel(result);
    }

    @Override
    public Phone update(Phone phone, Long phoneId) {
        PersonEntity personEntity = findById(phoneId).getPersonEntity();
        isValidRequestBody(phone);

        PhoneEntity phoneEntity = mapToEntity(phone);
        phoneEntity.setPhoneId(phoneId);
        phoneEntity.setPersonEntity(personEntity);

        return mapToModel(dao.update(phoneEntity));
    }

    @Override
    public void delete(Long phoneId) {
    	PhoneEntity phoneEntity = mapToEntity(findById(phoneId));
    	PersonEntity personEntity = phoneEntity.getPersonEntity();
    	
    	validateUserHasMorePhones(personEntity);
    
        personEntity.removePhone(phoneEntity);
        personDao.update(personEntity);
    }

    private PhoneEntity mapToEntity(Phone phone) {
        return PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
    }

    private Phone mapToModel(PhoneEntity phoneEntity) {
        return PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);
    }

    private void isValidRequestBody(Phone phone) {
        BindingResult bindingResult = new BindException(phone, "phone");

        validator.validate(phone, bindingResult);

        if(bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            throw new InvalidRequestBodyException(Phone.class, bindingResult);
        }
    }

    private void validateUserHasMorePhones(PersonEntity personEntity) {
    	if(personEntity.getPhones().size() < 2) {
    		throw new IllegalPhoneDeletionException();
    	}
    }
}
