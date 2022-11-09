package com.stgcodes.validation;

import com.stgcodes.model.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PhoneValidatorTests {

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

    @ParameterizedTest
    @ValueSource(strings = {"806-123-4567", "214-123-4567", "817-902-5478", "234-567-8901", "223-456-7890"})
    void testValidPhoneNumber(String value) {
        phone.setPhoneNumber(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "223-456-78901", "1234567890", "(806) 765 1234"})
    void testInvalidPhoneNumber(String value) {
        phone.setPhoneNumber(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("phonenumber.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MOBILE", "HOME", "BUSINESS"})
    void testValidPhoneType(String value) {
        phone.setPhoneType(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "home", "mobile", "business"})
    void testInvalidPhoneType(String value) {
        phone.setPhoneType(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("phonetype.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
}