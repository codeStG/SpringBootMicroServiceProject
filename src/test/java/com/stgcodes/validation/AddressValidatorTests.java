package com.stgcodes.validation;

import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class AddressValidatorTests {

    private Address address;
    private ValidatorTestUtils validatorTestUtils;
    private ResourceBundleMessageSource messageSource;
    private List<String> errors;

    @Before
    public void setUp() {
        address = Address.builder()
                .lineOne("5678 What St.")
                .lineTwo("Unit 8")
                .city("Atlanta")
                .state(GeographicState.GEORGIA)
                .zip("78019")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new AddressValidator(), address);
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    @Test
    public void testValidLineOne() {
        address.setLineOne("1234 David St.");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyLineOneIsInvalid() {
        address.setLineOne("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("lineone.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testLineOneTooLongIsInvalid() {
        address.setLineOne("Some Random Line One That Does Not Exist With A Bunch Of Lettersssssssssssss");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("lineone.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidLineTwo() {
        address.setLineTwo("Unit 4");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyLineTwoIsInvalid() {
        address.setLineTwo("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("linetwo.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testLineTwoTooLongIsInvalid() {
        address.setLineTwo("abcdefghijklmnopqrstuvwxyz");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("linetwo.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidCity() {
        address.setCity("Austin");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyCityIsInvalid() {
        address.setCity("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("city.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testCityTooLongIsInvalid() {
        address.setCity("Village of Grosse Pointe Shores City, A Michigan City Plussssssssssssssssssssssss");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("city.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

//    @Test
//    public void testValidStateName() {
//        address.setState(GeographicState.TEXAS);
//        errors = validatorTestUtils.getErrors();
//
//        String expected = messageSource.getMessage("errors.none", null, Locale.US);
//
//        assertEquals(expected, errors.get(0));
//    }
//
//    @Test
//    public void testValidStateAbbreviation() {
//        address.setState("Tx");
//        errors = validatorTestUtils.getErrors();
//
//        String expected = messageSource.getMessage("errors.none", null, Locale.US);
//
//        assertEquals(expected, errors.get(0));
//    }
//
//    @Test
//    public void testEmptyStateIsInvalid() {
//        address.setState("");
//        errors = validatorTestUtils.getErrors();
//
//        String expected = messageSource.getMessage("state.invalid", null, Locale.US);
//
//        assertEquals(expected, errors.get(0));
//    }
//
//    @Test
//    public void testInvalidState() {
//        address.setState("Oklahom");
//        errors = validatorTestUtils.getErrors();
//
//        String expected = messageSource.getMessage("state.invalid", null, Locale.US);
//
//        assertEquals(expected, errors.get(0));
//    }

    @Test
    public void testValid5DigitZip() {
        address.setZip("12345");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValid9DigitZip() {
        address.setZip("12345-6789");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyZipIsInvalid() {
        address.setZip("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("zip.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testZipLessThan5Digits() {
        address.setZip("7601");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("zip.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testZipLongerThan9Digits() {
        address.setZip("12345-67890");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("zip.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testZipBetween5And9Digits() {
        address.setZip("12345-678");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("zip.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
}