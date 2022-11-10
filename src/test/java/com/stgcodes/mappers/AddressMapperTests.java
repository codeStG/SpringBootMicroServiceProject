package com.stgcodes.mappers;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressMapperTests {

    @Test
    void testMapModelToEntity() {
        Address address = Address.builder()
                .lineOne("5678 What St.")
                .lineTwo("Unit 8")
                .city("Atlanta")
                .state(GeographicState.GA)
                .zip("78019")
                .build();

        AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(address);

        Assertions.assertEquals(address.getLineOne(), addressEntity.getLineOne());
        Assertions.assertEquals(address.getLineTwo(), addressEntity.getLineTwo());
        Assertions.assertEquals(address.getCity(), addressEntity.getCity());
        Assertions.assertEquals(address.getState(), addressEntity.getState());
        Assertions.assertEquals(address.getZip(), addressEntity.getZip());
    }

    @Test
    void testMapEntityToModel() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setLineOne("1234 Some Rd.");
        addressEntity.setLineTwo("");
        addressEntity.setCity("Boulder");
        addressEntity.setState(GeographicState.CO);
        addressEntity.setZip("80301");

        Address address = AddressMapper.INSTANCE.addressEntityToAddress(addressEntity);

        Assertions.assertEquals(addressEntity.getLineOne(), address.getLineOne());
        Assertions.assertEquals(addressEntity.getLineTwo(), address.getLineTwo());
        Assertions.assertEquals(addressEntity.getCity(), address.getCity());
        Assertions.assertEquals(addressEntity.getState(), address.getState());
        Assertions.assertEquals(addressEntity.getZip(), address.getZip());
    }

    @Test
    void testMapNullModelToNullEntity() {
        Address address = null;

        AddressEntity addressEntity = AddressMapper.INSTANCE.addressToAddressEntity(address);

        Assertions.assertNull(addressEntity);
    }

    @Test
    void testMapNullEntityToNullModel() {
        AddressEntity addressEntity = null;

        Address address = AddressMapper.INSTANCE.addressEntityToAddress(addressEntity);

        Assertions.assertNull(address);
    }
}
