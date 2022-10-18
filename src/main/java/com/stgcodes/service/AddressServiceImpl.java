package com.stgcodes.service;

import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import com.stgcodes.utils.FieldFormatter;
import com.stgcodes.validation.AddressValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        if (addressEntity == null) {
            log.info("ID " + addressId + " does not exist");
            throw new IdNotFoundException();
        }

        return mapToModel(addressEntity);
    }

    @Override
    public Address save(Address address) {
        if(isValidRequestBody(address)) {
            dao.save(mapToEntity(address));
            return address;
        }

        throw new InvalidRequestBodyException();
    }

    @Override
    public Address update(Address address, Long addressId) {
        findById(addressId);

        AddressEntity addressEntity = mapToEntity(address);
        addressEntity.setAddressId(addressId);

        dao.update(addressEntity);
        return address;
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

    private void cleanAddress(Address address) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        address.setLineOne(address.getLineOne().trim());
        address.setLineTwo(address.getLineTwo().trim());
        address.setCity(address.getCity().trim());
        address.setState(fieldFormatter.formatAsState(address.getState()));
        address.setZip(fieldFormatter.separateBy(address.getZip(), "-"));
    }

    private boolean isValidRequestBody(Address address) {
        BindingResult bindingResult = new BindException(address, "address");

        cleanAddress(address);
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