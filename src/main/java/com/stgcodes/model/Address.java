package com.stgcodes.model;

import com.stgcodes.validation.enums.GeographicState;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

	private Long addressId;
    private String lineOne;
    private String lineTwo;
    private String city;
    private GeographicState state;
    private String zip;
}
