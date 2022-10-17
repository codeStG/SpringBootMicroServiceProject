package com.stgcodes.endpoint;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.model.Address;
import com.stgcodes.service.AddressService;
import com.stgcodes.validation.AddressValidator;
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
@RequestMapping("/addresses")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    private AddressValidator validator;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();

        service.findAll().forEach(e -> addresses.add(service.mapToModel(e)));

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Address> getAddress(@RequestParam Long addressId) {
        Address address = service.mapToModel(service.findById(addressId));

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        if(isValidRequestBody(address)) {
            service.save(service.mapToEntity(address));
            return new ResponseEntity<>(address, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @RequestParam Long addressId) {
        if(isValidRequestBody(address)) {
            service.findById(addressId);
            AddressEntity addressEntity = service.mapToEntity(address);
            addressEntity.setAddressId(addressId);
            service.update(addressEntity);
            return new ResponseEntity<>(address, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Address> deleteAddress(@RequestParam Long addressId) {
        service.delete(addressId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isValidRequestBody(Address address) {
        BindingResult bindingResult = new BindException(address, "address");

        service.cleanAddress(address);
        validator.validate(address, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("address.invalid", null, Locale.US));
            bindingResult.getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            return false;
        }

        return true;
    }
}