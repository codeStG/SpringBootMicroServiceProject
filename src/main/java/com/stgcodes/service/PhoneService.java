package com.stgcodes.service;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Phone;

public interface PhoneService extends GenericService<PhoneEntity> {
    void cleanPhone(Phone phone);
    PhoneEntity mapToEntity(Phone phone);
    Phone mapToModel(PhoneEntity phoneEntity);
}

