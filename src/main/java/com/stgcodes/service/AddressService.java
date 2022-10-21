package com.stgcodes.service;

import com.stgcodes.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address findById(Long addressId);
    Address save(Address address);
    Address update(Address address, Long addressId);
    void delete(Long addressId);
}