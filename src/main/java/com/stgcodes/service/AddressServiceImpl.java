package com.stgcodes.service;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import com.stgcodes.utils.FieldFormatter;
import org.springframework.stereotype.Component;

@Component("addressService")
public class AddressServiceImpl extends GenericServiceImpl<AddressEntity> implements AddressService {

    @Override
    public Address cleanAddress(Address address) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        return Address.builder()
                .lineOne(fieldFormatter.cleanWhitespace(address.getLineOne()))
                .lineTwo(fieldFormatter.cleanWhitespace(address.getLineTwo()))
                .city(fieldFormatter.cleanWhitespace(address.getCity()))
                .state(fieldFormatter.formatAsEnum(address.getState()))
                .zip(fieldFormatter.separateBy(address.getZip(), "-"))
                .build();
    }

    @Override
    public AddressEntity mapToEntity(Address address) {
        return AddressMapper.INSTANCE.addressToAddressEntity(address);
    }

    @Override
    public Address mapToModel(AddressEntity addressEntity) {
        return AddressMapper.INSTANCE.addressEntityToAddress(addressEntity);
    }
}