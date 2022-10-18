package com.stgcodes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {
    enum PhoneType {
        MOBILE,
        HOME,
        BUSINESS
    }

    private String phoneNumber;
    private String phoneType;
}
