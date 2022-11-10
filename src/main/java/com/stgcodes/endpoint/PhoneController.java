package com.stgcodes.endpoint;

import com.stgcodes.exception.DataAccessException;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
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
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Phone> getPhone(@RequestParam Long phoneId) throws IdNotFoundException {
        return new ResponseEntity<>(service.findById(phoneId), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone, @RequestParam(defaultValue = "-1") Long personId) throws InvalidRequestBodyException, IdNotFoundException, DataAccessException {
        return new ResponseEntity<>(service.save(phone, personId), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone, @RequestParam Long phoneId) throws InvalidRequestBodyException, IdNotFoundException, DataAccessException {
        return new ResponseEntity<>(service.update(phone, phoneId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Phone> deletePhone(@RequestParam Long phoneId) {
        service.delete(phoneId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}