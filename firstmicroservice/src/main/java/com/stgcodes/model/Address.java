package com.stgcodes.model;

import lombok.Data;

@Data
public class Address {

    private String lineOne;
    private String lineTwo;
    private String city;
    private String state;
    private String zip;
}
