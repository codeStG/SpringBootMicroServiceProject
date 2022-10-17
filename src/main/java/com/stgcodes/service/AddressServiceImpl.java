package com.stgcodes.service;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import com.stgcodes.utils.FieldFormatter;
import org.springframework.stereotype.Component;

@Component("addressService")
public class AddressServiceImpl extends GenericServiceImpl<AddressEntity> implements AddressService {

    @Override
    public void cleanAddress(Address address) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        address.setLineOne(address.getLineOne().trim());
        address.setLineTwo(address.getLineTwo().trim());
        address.setCity(address.getCity().trim());
        address.setState(fieldFormatter.formatAsState(address.getState()));
        address.setZip(fieldFormatter.separateBy(address.getZip(), "-"));
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