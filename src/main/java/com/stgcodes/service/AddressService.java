package com.stgcodes.service;

import com.stgcodes.model.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAllAddresses();
    Address getAddressById(Long personId);
    Address addAddress(Address address);
    Address deleteAddress(Long addressId);
}

