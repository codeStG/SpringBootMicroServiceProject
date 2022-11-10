package com.stgcodes.mappers;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.enums.PhoneType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneMapperTests {

    @Test
    void testMapModelToEntity() {
        Phone phone = Phone.builder()
                .phoneNumber("123-456-7890")
                .phoneType(PhoneType.MOBILE)
                .build();

        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);

        Assertions.assertEquals(phone.getPhoneNumber(), phoneEntity.getPhoneNumber());
        Assertions.assertEquals(phone.getPhoneType(), phoneEntity.getPhoneType());
    }

    @Test
    void testMapEntityToModel() {
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setPhoneNumber("098-765-4321");
        phoneEntity.setPhoneType(PhoneType.BUSINESS);

        Phone phone = PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);

        Assertions.assertEquals(phoneEntity.getPhoneNumber(), phone.getPhoneNumber());
        Assertions.assertEquals(phoneEntity.getPhoneType(), phone.getPhoneType());
    }

    @Test
    void testMapNullModelToNullEntity() {
        Phone phone = null;

        PhoneEntity phoneEntity = PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);

        Assertions.assertNull(phoneEntity);
    }

    @Test
    void testMapNullEntityToNullModel() {
        PhoneEntity phoneEntity = null;

        Phone phone = PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);

        Assertions.assertNull(phone);
    }
}
