package com.stgcodes.model;

import com.stgcodes.entity.PhoneEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {
    private Long phoneId;
    enum PhoneType {
        MOBILE,
        HOME,
        BUSINESS
    }

    private String phoneNumber;
    private String phoneType;
    private PhoneEntity phoneEntity;
}
