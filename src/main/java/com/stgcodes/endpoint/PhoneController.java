package com.stgcodes.endpoint;

import com.stgcodes.model.Phone;
import com.stgcodes.service.PhoneService;
import com.stgcodes.validation.PhoneValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Phone> phones = service.findAll();
        return new ResponseEntity<>(phones, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Phone> getPhone(@RequestParam Long phoneId) {
        Phone phone = service.findById(phoneId);
        return new ResponseEntity<>(phone, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone, @RequestParam Long personId) {
        Phone refreshedPhone = service.save(phone, personId);
        return new ResponseEntity<>(refreshedPhone, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone, @RequestParam Long phoneId) {
        Phone refreshedPhone = service.update(phone, phoneId);
        return new ResponseEntity<>(refreshedPhone, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Phone> deletePhone(@RequestParam Long phoneId) {
        service.delete(phoneId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}