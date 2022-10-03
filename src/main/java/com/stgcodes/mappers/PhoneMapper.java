package com.stgcodes.mappers;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhoneMapper {

    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    PhoneEntity phoneToPhoneEntity(Phone phone);

    Phone phoneEntityToPhone(PhoneEntity phoneEntity);
}
