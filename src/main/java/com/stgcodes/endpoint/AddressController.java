package com.stgcodes.endpoint;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
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

        for(AddressEntity addressEntity : service.findAll()) {
            addresses.add(AddressMapper.INSTANCE.addressEntityToAddress(addressEntity));
        }

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Address> getAddress(@RequestParam Long addressId) {
        AddressEntity addressEntity = service.findById(addressId);
        Address address = AddressMapper.INSTANCE.addressEntityToAddress(addressEntity);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        BindingResult bindingResult = new BindException(address, "address");
        validator.validate(address, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("person.invalid", null, Locale.US));
            bindingResult.getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        service.save(AddressMapper.INSTANCE.addressToAddressEntity(address));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Address> deleteAddress(@RequestParam Long addressId) {
        service.delete(addressId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}