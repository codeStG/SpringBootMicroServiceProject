package com.stgcodes.service;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.dao.PhoneDao;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.IllegalPhoneDeletionException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.utils.FieldFormatter;
import com.stgcodes.validation.PhoneValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

        if(phoneEntity == null) {
            log.info("ID " + phoneId + " does not exist");
            throw new IdNotFoundException();
        }

        return mapToModel(phoneEntity);
    }

    @Override
    public Phone save(Phone phone, Long personId) {
        PersonEntity personEntity = personDao.findById(personId);

        if(personEntity == null) {
            log.info("ID " + personId + " does not exist");
            throw new IdNotFoundException();
        }

        if(isValidRequestBody(phone)) {
            PhoneEntity phoneEntity = mapToEntity(phone);
            personEntity.addPhone(phoneEntity);
            return mapToModel(dao.save(phoneEntity));
        }

        throw new InvalidRequestBodyException();
    }

    @Override
    public Phone update(Phone phone, Long phoneId) {
        PersonEntity personEntity = findById(phoneId).getPersonEntity();

        PhoneEntity phoneEntity = mapToEntity(phone);
        phoneEntity.setPhoneId(phoneId);
        phoneEntity.setPersonEntity(personEntity);

        return mapToModel(dao.update(phoneEntity));
    }

    @Override
    public void delete(Long phoneId) {
        if (!personHasMoreThanOnePhone(phoneId)) {
            log.info("Must have at least one phone attached to user account");
            throw new IllegalPhoneDeletionException();
        }

        PhoneEntity phoneEntity = mapToEntity(findById(phoneId));
        PersonEntity personEntity = phoneEntity.getPersonEntity();
        personEntity.removePhone(phoneEntity);
        personDao.update(personEntity);
    }

    private PhoneEntity mapToEntity(Phone phone) {
        return PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
    }

    private Phone mapToModel(PhoneEntity phoneEntity) {
        return PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);
    }

    private void cleanPhone(Phone phone) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        phone.setPhoneNumber(phone.getPhoneNumber().trim());
        phone.setPhoneType(fieldFormatter.formatAsEnum(phone.getPhoneType()));
    }

    private boolean isValidRequestBody(Phone phone) {
        BindingResult bindingResult = new BindException(phone, "phone");

        cleanPhone(phone);
        validator.validate(phone, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("phone.invalid", null, Locale.US));
            bindingResult.getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            return false;
        }

        return true;
    }

    private boolean personHasMoreThanOnePhone(Long phoneId) {
        Phone phone = findById(phoneId);
        PersonEntity personEntity = mapToEntity(phone).getPersonEntity();

        return personEntity.getPhones().size() > 1;
    }

}
