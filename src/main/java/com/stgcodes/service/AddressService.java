package com.stgcodes.service;

import com.stgcodes.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    List<Address> getAllAddresses();
    Address getAddressById(Long personId);
    Address addAddress(Address address);
    void deleteAddress(Long addressId);
}

