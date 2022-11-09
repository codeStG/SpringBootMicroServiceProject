package com.stgcodes.endpoint;

import com.stgcodes.model.Address;
import com.stgcodes.service.AddressService;
import com.stgcodes.validation.AddressValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    private AddressValidator validator;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = service.findAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Address> getAddress(@RequestParam Long addressId) {
        Address address = service.findById(addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        service.save(address);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @RequestParam Long addressId) {
            service.update(address, addressId);
            return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Address> deleteAddress(@RequestParam Long addressId) {
        service.delete(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}