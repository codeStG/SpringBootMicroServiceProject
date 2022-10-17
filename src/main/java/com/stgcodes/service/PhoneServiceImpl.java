package com.stgcodes.service;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import com.stgcodes.utils.FieldFormatter;
import org.springframework.stereotype.Component;

@Component("phoneService")
public class PhoneServiceImpl extends GenericServiceImpl<PhoneEntity> implements PhoneService {

    @Override
    public void cleanPhone(Phone phone) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        phone.setPhoneNumber(phone.getPhoneNumber().trim());
        phone.setPhoneType(fieldFormatter.formatAsEnum(phone.getPhoneType()));
    }

    @Override
    public PhoneEntity mapToEntity(Phone phone) {
        return PhoneMapper.INSTANCE.phoneToPhoneEntity(phone);
    }

    @Override
    public Phone mapToModel(PhoneEntity phoneEntity) {
        return PhoneMapper.INSTANCE.phoneEntityToPhone(phoneEntity);
    }
}
