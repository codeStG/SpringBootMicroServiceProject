package com.stgcodes.endpoint;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Phone;
import com.stgcodes.service.PhoneService;
import com.stgcodes.validation.PhoneValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/phones")
@Slf4j
public class PhoneController {

    @Autowired
    private PhoneService service;

    @Autowired
    private PhoneValidator validator;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Phone>> getAllPhones() {
        List<Phone> phones = new ArrayList<>();

        service.findAll().forEach(e -> phones.add(service.mapToModel(e)));

        return new ResponseEntity<>(phones, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Phone> getPhone(@RequestParam Long phoneId) {
        Phone phone = service.mapToModel(service.findById(phoneId));

        return new ResponseEntity<>(phone, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone) {
        if(isValidRequestBody(phone)) {
            service.save(service.mapToEntity(phone));
            return new ResponseEntity<>(phone, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone, @RequestParam Long phoneId) {
        if(isValidRequestBody(phone)) {
            service.findById(phoneId);
            PhoneEntity phoneEntity = service.mapToEntity(phone);
            phoneEntity.setPhoneId(phoneId);
            service.update(phoneEntity);
            return new ResponseEntity<>(phone, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Phone> deletePhone(@RequestParam Long phoneId) {
        service.delete(phoneId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isValidRequestBody(Phone phone) {
        BindingResult bindingResult = new BindException(phone, "phone");

        service.cleanPhone(phone);
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
}