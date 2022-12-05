package com.stgcodes.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stgcodes.exception.DataAccessException;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Phone;
import com.stgcodes.service.PhoneService;

@RestController
@RequestMapping("/phones")
public class PhoneController {

    @Autowired
    private PhoneService service;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Phone>> getAllPhones() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Phone> getPhone(@RequestParam Long phoneId) throws IdNotFoundException {
        return new ResponseEntity<>(service.findById(phoneId), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone, @RequestParam(defaultValue = "-1") Long userId) throws InvalidRequestBodyException, IdNotFoundException, DataAccessException {
        return new ResponseEntity<>(service.save(phone, userId), HttpStatus.CREATED);
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