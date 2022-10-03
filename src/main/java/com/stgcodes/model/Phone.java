package com.stgcodes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {
    enum PhoneType {
        MOBILE,
        HOME,
        BUSINESS,
        OTHER
    }

    //Should this be split into area code and phone number
    private String phoneNumber;
    private String phoneType;
}
