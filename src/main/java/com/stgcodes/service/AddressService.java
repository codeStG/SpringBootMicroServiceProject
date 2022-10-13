package com.stgcodes.service;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.model.Address;

public interface AddressService extends GenericService<AddressEntity> {
    void cleanAddress(Address address);
    AddressEntity mapToEntity(Address address);
    Address mapToModel(AddressEntity addressEntity);
}