package com.stgcodes.service;

import com.stgcodes.dao.AddressDao;
import com.stgcodes.entity.AddressEntity;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao dao;

    @Override
    public List<Address> getAllAddresses() {
        List<AddressEntity> addressEntities = dao.getAddresses();
        List<Address> addresses = new ArrayList<>();

        for(AddressEntity addressEntity : addressEntities) {
            addresses.add(AddressMapper.INSTANCE.addressEntityToAddress(addressEntity));
        }

        return addresses;
    }

    @Override
    public Address getAddressById(Long addressId) {
        AddressEntity addressEntity = dao.getAddressById(addressId);
        Address address = null;

        if(addressEntity == null) {
            log.info("ID " + addressId + " does not exist");
            throw new IdNotFoundException();
        } else {
            address = AddressMapper.INSTANCE.addressEntityToAddress(addressEntity);
        }

        return address;
    }

    @Override
    public Address addAddress(Address address) {
        AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(address);

        return AddressMapper.INSTANCE.addressEntityToAddress(dao.addAddress(addressEntity));
    }

    @Override
    public Address deleteAddress(Long addressId) {
        AddressEntity addressEntity = dao.getAddressById(addressId);

        return AddressMapper.INSTANCE.addressEntityToAddress(dao.deleteAddress(addressEntity));
    }
}
