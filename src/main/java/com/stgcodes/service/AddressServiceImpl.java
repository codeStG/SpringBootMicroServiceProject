package com.stgcodes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import com.stgcodes.validation.AddressValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao dao;

    @Autowired
    private AddressValidator validator;

    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        dao.findAll().forEach(e -> addresses.add(mapToModel(e)));

        return addresses;
    }

    @Override
    public Address findById(Long addressId) {
        AddressEntity addressEntity = dao.findById(addressId);

        return mapToModel(addressEntity);
    }

    @Override
    public Address save(Address address) {
        isValidRequestBody(address);
        AddressEntity addressEntity = mapToEntity(address);
        AddressEntity result = dao.save(addressEntity);

        return mapToModel(result);
    }

    @Override
    public Address update(Address address, Long addressId) {
        findById(addressId);
        isValidRequestBody(address);

        AddressEntity addressEntity = mapToEntity(address);
        addressEntity.setAddressId(addressId);

        AddressEntity result = dao.update(addressEntity);

        return mapToModel(result);
    }

    @Override
    public void delete(Long addressId) {
        Address address = findById(addressId);
        dao.delete(mapToEntity(address));
    }

    private AddressEntity mapToEntity(Address address) {
        return AddressMapper.INSTANCE.addressToAddressEntity(address);
    }

    private Address mapToModel(AddressEntity addressEntity) {
        return AddressMapper.INSTANCE.addressEntityToAddress(addressEntity);
    }

    private void isValidRequestBody(Address address) {
        BindingResult bindingResult = new BindException(address, "address");

        validator.validate(address, bindingResult);

        if(bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            throw new InvalidRequestBodyException(Address.class, bindingResult);
        }
    }
}