package com.stgcodes.service;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.model.Address;
import com.stgcodes.model.Person;

public interface AddressService extends GenericService<AddressEntity> {
    Address cleanAddress(Address address);
    AddressEntity mapToEntity(Address address);
    Address mapToModel(AddressEntity addressEntity);
}