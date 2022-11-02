package com.stgcodes.validation;

import com.stgcodes.model.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PhoneValidatorTests {

    private Phone phone;
    private ValidatorTestUtils validatorTestUtils;
    private ResourceBundleMessageSource messageSource;
    private List<String> errors;


    @BeforeEach
    public void setUp() {
        phone = Phone.builder()
                .phoneNumber("223-456-7890")
                .phoneType("MOBILE")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new PhoneValidator(), phone);
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    @Test
    public void testValidPhoneNumber() {
        phone.setPhoneNumber("234-567-8901");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyPhoneNumberIsInvalid() {
        phone.setPhoneNumber("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("phonenumber.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testInvalidPhoneNumber() {
        phone.setPhoneNumber("223-456-78901");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("phonenumber.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidPhoneType() {
        phone.setPhoneType("HOME");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyPhoneTypeIsInvalid() {
        phone.setPhoneType("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("phonetype.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testInvalidPhoneType() {
        phone.setPhoneType("Office");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("phonetype.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
}