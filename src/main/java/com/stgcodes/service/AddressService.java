package com.stgcodes.service;

import java.util.List;

import com.stgcodes.model.Address;

public interface AddressService {
    List<Address> findAll();
    Address findById(Long addressId);
    Address save(Address address);
    Address update(Address address, Long addressId);
    void delete(Long addressId);
}