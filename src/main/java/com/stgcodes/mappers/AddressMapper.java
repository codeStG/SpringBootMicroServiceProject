package com.stgcodes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.model.Address;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressEntity addressToAddressEntity(Address address);

    Address addressEntityToAddress(AddressEntity addressEntity);
}
