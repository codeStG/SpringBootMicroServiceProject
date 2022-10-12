package com.stgcodes.service;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import org.springframework.stereotype.Component;

@Component("phoneService")
public class PhoneServiceImpl extends GenericServiceImpl<PhoneEntity> implements PhoneService {

    //TODO: implement cleanPhone method
    @Override
    public Phone cleanPhone(Phone phone) {
        return phone;
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
