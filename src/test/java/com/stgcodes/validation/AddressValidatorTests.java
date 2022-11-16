package com.stgcodes.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;

class AddressValidatorTests {

    private Address address;
    private ValidatorTestUtils validatorTestUtils;
    private ResourceBundleMessageSource messageSource;
    private List<String> errors;

    @BeforeEach
    public void setUp() {
        address = Address.builder()
                .lineOne("5678 What St.")
                .lineTwo("Unit 8")
                .city("Atlanta")
                .state(GeographicState.GA)
                .zip("78019")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new AddressValidator(), address);
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }
    
    @Test
    void testValidatorSupportsAddress() {
    	AddressValidator validator = new AddressValidator();

    	assertTrue(validator.supports(Address.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234 David St.", "111 Montgomery Rd.", "P.O. Box 893", "This is acceptable", "   384 Richard Ave.   "})
    void testValidLineOne(String value) {
        address.setLineOne(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Some Random Line One That Does Not Exist With A Bunch Of Lettersssssssssssss"})
    void testInvalidLineOne(String value) {
        address.setLineOne(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("lineone.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "Unit 4", "Apt. 3", "No. 1", "   Unit 189   ", "This is acceptable input"})
    void testValidLineTwo(String value) {
        address.setLineTwo(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
    
    @Test
    void testInvalidLineTwo() {
        address.setLineTwo("abcdefghijklmnopqrstuvwxyz");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("linetwo.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Austin", "New York City", "Raleigh", "This is acceptable", "Miami"})
    void testValidCity(String value) {
        address.setCity(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Village of Grosse Pointe Shores City, A Michigan City Plussssssssssssssssssssssss"})
    void testEmptyCityIsInvalid(String value) {
        address.setCity(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("city.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "76017", "75052", "89013", "54321"})
    void testValid5DigitZip(String value) {
        address.setZip(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345-6789", "76017-1234", "33101-0119", "27513-1100", "11385-9994"})
    void testValid9DigitZip(String value) {
        address.setZip(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "7601", "12345-67890", "12345-678", "12345-6", "12345-", "12345-67", "1", "12", "123"})
    void testEmptyZipIsInvalid() {
        address.setZip("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("zip.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
}