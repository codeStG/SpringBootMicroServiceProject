package com.stgcodes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String lineOne;
    private String lineTwo;
    private String city;
    private String state;
    private String zip;
}
