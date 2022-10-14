package com.stgcodes.validation;

import com.stgcodes.model.Address;
import com.stgcodes.model.Phone;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PhoneValidatorTests {

    private Phone phone;
    private ValidatorTestUtils validatorTestUtils;
    private Map<String, String> errors;

    @Before
    public void setUp() {
        phone = Phone.builder()
                .phoneNumber("123-456-7890")
                .phoneType("MOBILE")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new PhoneValidator(), phone);

        errors = new HashMap<>();
    }

    @Test
    public void testValidPhoneNumber() {
        phone.setPhoneNumber("234-567-8901");
        errors = validatorTestUtils.getErrors("phoneNumber", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyPhoneNumberIsInvalid() {
        phone.setPhoneNumber("");
        errors = validatorTestUtils.getErrors("phoneNumber", "phonenumber.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testInvalidPhoneNumber() {
        phone.setPhoneNumber("123-456-78901");
        errors = validatorTestUtils.getErrors("phoneNumber", "phonenumber.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidPhoneType() {
        phone.setPhoneType("HOME");
        errors = validatorTestUtils.getErrors("phoneType", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyPhoneTypeIsInvalid() {
        phone.setPhoneType("");
        errors = validatorTestUtils.getErrors("phoneType", "phonetype.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testInvalidPhoneType() {
        phone.setPhoneType("Office");
        errors = validatorTestUtils.getErrors("phoneType", "phonetype.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }
}