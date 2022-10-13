package com.stgcodes.validation;

import com.stgcodes.model.Address;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AddressValidatorTests {

    private Address address;
    private ValidatorTestUtils validatorTestUtils;
    private Map<String, String> errors;

    @Before
    public void setUp() {
        address = Address.builder()
                .lineOne("5678 What St.")
                .lineTwo("Unit 8")
                .city("Atlanta")
                .state("Georgia")
                .zip("78019")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new AddressValidator(), address);

        errors = new HashMap<>();
    }

    @Test
    public void testValidLineOne() {
        String testCase = "1234 Davis St.";

        address.setLineOne(testCase);
        errors = validatorTestUtils.getErrors("lineOne", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyLineOneIsInvalid() {
        address.setLineOne("");
        errors = validatorTestUtils.getErrors("lineOne", "lineone.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLineOneTooLongIsInvalid() {
        String testCase = "Some Random Line One That Does Not Exist With A Bunch Of Lettersssssssssssss";

        address.setLineOne(testCase);
        errors = validatorTestUtils.getErrors("lineOne", "lineone.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidLineTwo() {
        String testCase = "Unit 4";

        address.setLineTwo(testCase);
        errors = validatorTestUtils.getErrors("lineTwo", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyLineTwoIsInvalid() {
        address.setLineTwo("");
        errors = validatorTestUtils.getErrors("lineTwo", "linetwo.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLineTwoTooLongIsInvalid() {
        String testCase = "abcdefghijklmnopqrstuvwxyz";

        address.setLineTwo(testCase);
        errors = validatorTestUtils.getErrors("lineTwo", "linetwo.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidCity() {
        String testCase = "Austin";

        address.setCity(testCase);
        errors = validatorTestUtils.getErrors("city", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyCityIsInvalid() {
        address.setCity("");
        errors = validatorTestUtils.getErrors("city", "city.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testCityTooLongIsInvalid() {
        String testCase = "Village of Grosse Pointe Shores City, A Michigan City Plussssssssssssssssssssssss";

        address.setCity(testCase);
        errors = validatorTestUtils.getErrors("city", "city.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidStateName() {
        String testCase = "Texas";

        address.setState(testCase);
        errors = validatorTestUtils.getErrors("state", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidStateAbbreviation() {
        String testCase = "Tx";

        address.setState(testCase);
        errors = validatorTestUtils.getErrors("state", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyStateIsInvalid() {
        address.setState("");
        errors = validatorTestUtils.getErrors("state", "state.invalid");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testInvalidState() {
        String testCase = "Oklahom";

        address.setState(testCase);
        errors = validatorTestUtils.getErrors("state", "state.invalid");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValid5DigitZip() {
        String testCase = "12345";

        address.setZip(testCase);
        errors = validatorTestUtils.getErrors("zip", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValid9DigitZip() {
        String testCase = "12345-6789";

        address.setZip(testCase);
        errors = validatorTestUtils.getErrors("zip", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyZipIsInvalid() {
        address.setZip("");
        errors = validatorTestUtils.getErrors("zip", "zip.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testZipLessThan5Digits() {
        String testCase = "7601";

        address.setZip(testCase);
        errors = validatorTestUtils.getErrors("zip", "zip.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testZipLongerThan9Digits() {
        String testCase = "12345-67890";

        address.setZip(testCase);
        errors = validatorTestUtils.getErrors("zip", "zip.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testZipBetween5And9Digits() {
        String testCase = "12345-678";

        address.setZip(testCase);
        errors = validatorTestUtils.getErrors("zip", "zip.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }
}