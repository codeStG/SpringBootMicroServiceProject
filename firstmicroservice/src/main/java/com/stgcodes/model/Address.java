package com.stgcodes.model;

import lombok.Data;

@Data
public class Address {

    private String lineOne;
    private String lineTwo;
    private String city;
    private String state;
    private String zip;

    public Address() {

    }

    public Address(String lineOne, String lineTwo, String city, String state, String zip) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
