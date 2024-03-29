package com.stgcodes.model;

import com.stgcodes.entity.UserEntity;
import com.stgcodes.validation.enums.PhoneType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {
    private Long phoneId;
    private String phoneNumber;
    private PhoneType phoneType;
    private UserEntity userEntity;
}