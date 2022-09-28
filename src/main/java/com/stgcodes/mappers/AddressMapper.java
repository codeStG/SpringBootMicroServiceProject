package com.stgcodes.mappers;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.entity.PersonEntity;
import com.stgcodes.model.Address;
import com.stgcodes.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressEntity addressToAddressEntity(Address address);

    Address addressEntityToAddress(AddressEntity addressEntity);
}
