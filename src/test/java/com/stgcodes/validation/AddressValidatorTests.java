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
    private BindingResult bindingResult;
    List<String> testCases;
    private Map<String, String> errors;

    @Before
    public void setUp() {
        address = new Address("5678 What St.", "Unit 8", "Atlanta", "Georgia", "78019");

        validatorTestUtils = new ValidatorTestUtils(new AddressValidator(), address);

        testCases = new ArrayList<>();
        errors = new HashMap<>();
    }

    @Test
    public void testValidLineOneValues() {
        testCases = Arrays.asList( "   1234 Davis St.     ", "   1\n8\n9 Lakewood Ave. ", "Driveoiqi398038y5utoiwa");

        for(String testCase : testCases) {
            address.setLineOne(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testEmptyLineOneIsInvalid() {
        address.setLineOne("");
        errors = validatorTestUtils.getErrors("lineOne", "lineone.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLineOneTooLongIsInvalid() {
        testCases = Arrays.asList("Some Random Line One That Does Not Exist With A Bunch Of Letters", "\n\n\n\n\n\nSome Random Line One That Does Not Exist       abcdefghijklmnopqrstu");

        for(String testCase : testCases) {
            address.setLineOne(testCase);
            errors = validatorTestUtils.getErrors("lineOne", "lineone.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidLineTwoValues() {
        testCases = Arrays.asList( "   Unit 4     ", "   A\np\nt\n 7 ", "abcdefghijklmnopqrstuvwxy");

        for(String testCase : testCases) {
            address.setLineTwo(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testEmptyLineTwoIsInvalid() {
        address.setLineTwo("");
        errors = validatorTestUtils.getErrors("lineTwo", "linetwo.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLineTwoTooLongIsInvalid() {
        testCases = Arrays.asList("abcdefghijklmnopqrstuvwxyz", "\n\n\n\n\n\nSome Random Line Two That Is Too Long");

        for(String testCase : testCases) {
            address.setLineTwo(testCase);
            errors = validatorTestUtils.getErrors("lineTwo", "linetwo.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidCityValues() {
        testCases = Arrays.asList( "Arlington", "Grand Prairie", "Mooselookmeguntic", "\n\n\n\n\n    Mooselookmeguntic        \n\n\n\n\n", "Village of Grosse Pointe Shores City, A Michigan City", "Washington-on-the-Brazos", "Winchester-on-the-Severn");

        for(String testCase : testCases) {
            address.setCity(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testEmptyCityIsInvalid() {
        address.setCity("");
        errors = validatorTestUtils.getErrors("city", "city.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testCityTooLongIsInvalid() {
        testCases = Arrays.asList("Village of Grosse Pointe Shores City, A Michigan City Plusss", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");

        for(String testCase : testCases) {
            address.setCity(testCase);
            errors = validatorTestUtils.getErrors("city", "city.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidStateValues() {
        testCases = Arrays.asList( "Texas", "Michigan", "Maine", "Tx", "SC", "North       \n\n\n\n     Carolina", "\n\n\n\nSouth Dakota       ", "   N   \n\n\n\n V\n\n");

        for(String testCase : testCases) {
            address.setState(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testEmptyStateIsInvalid() {
        address.setState("");
        errors = validatorTestUtils.getErrors("state", "state.invalid");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testFakeStateIsInvalid() {
        testCases = Arrays.asList("Georgeland", "Crumpet", "Fall   ");

        for(String testCase : testCases) {
            address.setState(testCase);
            errors = validatorTestUtils.getErrors("state", "state.invalid");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidZipValues() {
        testCases = Arrays.asList( "75017", "12345", "12345-6789", "\n\n\n\n\n    75013        \n\n\n\n\n", "84567", "99950-1234");

        for(String testCase : testCases) {
            address.setZip(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testEmptyZipIsInvalid() {
        address.setZip("");
        errors = validatorTestUtils.getErrors("zip", "zip.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testWrongLengthZipIsInvalid() {
        testCases = Arrays.asList("7601", "760175", "12345-678", "12345-67890");

        for(String testCase : testCases) {
            address.setZip(testCase);
            errors = validatorTestUtils.getErrors("zip", "zip.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }
}