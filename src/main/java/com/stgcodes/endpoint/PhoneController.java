package com.stgcodes.endpoint;

import com.stgcodes.model.Address;
import com.stgcodes.model.Phone;
import com.stgcodes.service.AddressService;
import com.stgcodes.service.PhoneService;
import com.stgcodes.validation.AddressValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/phones")
@Slf4j
public class PhoneController {

    @Autowired
    private PhoneService service;

//    @Autowired
//    private AddressValidator validator;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Phone>> getAllAddresses() {
        return new ResponseEntity<>(service.getAllPhones(), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Phone> getPhone(@RequestParam Long phoneId) {
        Phone phone = service.getPhoneById(phoneId);

        return phone == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(phone, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone) {
        BindingResult bindingResult = new BindException(phone, "address");
//        validator.validate(address, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("person.invalid", null, Locale.US));
            bindingResult.getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(service.addPhone(phone), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Phone> deletePhone(@RequestParam Long phoneId) {
        service.deletePhone(phoneId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}