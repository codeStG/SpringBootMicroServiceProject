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
import com.stgcodes.model.Address;
import com.stgcodes.service.AddressService;
import com.stgcodes.validation.AddressValidator;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    private AddressValidator validator;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Address> getAddress(@RequestParam Long addressId) {
        return new ResponseEntity<>(service.findById(addressId), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) throws InvalidRequestBodyException, DataAccessException {
        return new ResponseEntity<>(service.save(address), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @RequestParam Long addressId) throws InvalidRequestBodyException, IdNotFoundException, DataAccessException {
            return new ResponseEntity<>(service.update(address, addressId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Address> deleteAddress(@RequestParam Long addressId) {
        service.delete(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}