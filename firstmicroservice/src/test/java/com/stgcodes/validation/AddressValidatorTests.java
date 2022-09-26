package com.stgcodes.validation;

import com.stgcodes.model.Address;
import com.stgcodes.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AddressValidatorTests {

    private Address address;
    private AddressValidator addressValidator;
    private BindingResult bindingResult;
    private ResourceBundleMessageSource messageSource;
    List<String> testCases;
    private Map<String, String> errors;

    @Before
    public void setUp() {
        address = new Address("5678 What St.", "Unit 8", "Atlanta", "Georgia", "78019");

        addressValidator = new AddressValidator();

        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");

        testCases = new ArrayList<>();
        errors = new HashMap<>();
    }

    @Test
    public void testEmptyLineOneIsInvalid() {
        address.setLineOne("");
        errors = getErrors("lineOne", "lineone.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLineOneTooLongIsInvalid() {
        testCases = Arrays.asList("Some Random Line One That Does Not Exist With A Bunch Of Letters", "\n\n\n\n\n\nSome Random Line One That Does Not Exist       abcdefghijklmnopqrstu");

        for(String testCase : testCases) {
            address.setLineOne(testCase);
            errors = getErrors("lineOne", "lineone.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidLineOneValues() {
        testCases = Arrays.asList( "   1234 Davis St.     ", "   1\n8\n9 Lakewood Ave. ", "Driveoiqi398038y5utoiwa");

        for(String testCase : testCases) {
            address.setLineOne(testCase);
            performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

//
//    @Test
//    public void testEmptyLastNameIsInvalid() {
//        person.setLastName("");
//        errors = getErrors("lastName", "name.format");
//
//        assertEquals(errors.get("expectedError"), errors.get("actualError"));
//    }
//
//    @Test
//    public void testLastNameWithNonLetterCharacterIsInvalid() {
//        testCases = Arrays.asList("G3orge", "G/orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n ");
//
//        for(String testCase : testCases) {
//            person.setLastName(testCase);
//            errors = getErrors("lastName", "name.format");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testLastNameWithWhitespaceIsTrimmed() {
//        testCases = Arrays.asList( "   George     ", "   G \n E \n O \n ");
//
//        for(String testCase : testCases) {
//            person.setLastName(testCase);
//            performValidation();
//
//            assertEquals(0, bindingResult.getErrorCount());
//        }
//    }
//
//    @Test
//    public void testLongLastNameIsTruncated() {
//        testCases = Arrays.asList( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12");
//
//        for(String testCase : testCases) {
//            person.setLastName(testCase);
//            performValidation();
//
//            assertEquals(25, person.getLastName().length());
//        }
//    }
//
//    @Test
//    public void testWrongLengthUsernameIsInvalid() {
//        testCases = Arrays.asList("  ", "   Georg", "\n\n\n\n    George w twenty six characters", "\n\n\n\n\n    ");
//
//        for(String testCase : testCases) {
//            person.setUsername(testCase);
//            errors = getErrors("username", "username.format");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testWrongDateFormatIsInvalid() {
//        testCases = Arrays.asList("    ", "\n\n\n\n   ", "\n    08 sldi \n", "08-19-2022", "';[]'/.;'[", "08/32/2022");
//
//        for(String testCase : testCases) {
//            person.setDateOfBirth(testCase);
//            errors = getErrors("dateOfBirth", "date.format");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testFutureDateIsInvalid() {
//        testCases = Arrays.asList("10/31/3200", "12/13/9600", "07/11/2096");
//
//        for(String testCase : testCases) {
//            person.setDateOfBirth(testCase);
//            errors = getErrors("dateOfBirth", "date.future");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testWrongSocialSecurityFormatIsInvalid() {
//        testCases = Arrays.asList("000-00-0000", "999-99-9999", "      ", "\n\n\n\n\n      ", "03975395873250928559", "123456789");
//
//        for(String testCase : testCases) {
//            person.setSocialSecurityNumber(testCase);
//            errors = getErrors("socialSecurityNumber", "ssn.format");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testValidSocialSecurityNumbers() {
//        testCases = Arrays.asList("845-67-8923", "682-67-4598", "523-91-4835", "401-83-2968");
//
//        for(String testCase : testCases) {
//            person.setSocialSecurityNumber(testCase);
//            performValidation();
//
//            assertEquals(0, bindingResult.getErrorCount());
//        }
//    }
//
//    @Test
//    public void testGenderOtherThanEnumIsInvalid() {
//        testCases = Arrays.asList("man", "woman", "i dont know", "    ", "\n\n\n\n\n   sjdkhd", "\n\n\n");
//
//        for(String testCase : testCases) {
//            person.setGender(testCase);
//            errors = getErrors("gender", "gender.format");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testValidGender() {
//        testCases = Arrays.asList("male", "female", "refuse", "mAle", "f e m     a    le", "r\ne\nf\nu\ns\ne");
//
//        for(String testCase : testCases) {
//            person.setGender(testCase);
//            performValidation();
//
//            assertEquals(0, bindingResult.getErrorCount());
//        }
//    }
//
//    @Test
//    public void testWrongEmailFormatIsInvalid() {
//        testCases = Arrays.asList("getorres@teksystems", "getorresteksystems.com", "georgie@localhost", "georgie@127.0.0.1");
//
//        for(String testCase : testCases) {
//            person.setEmail(testCase);
//            errors = getErrors("email", "email.format");
//
//            assertEquals(errors.get("expectedError"), errors.get("actualError"));
//        }
//    }
//
//    @Test
//    public void testValidEmail() {
//        testCases = Arrays.asList("getorres@teksystems.com", "who@what.org", "me@gmail.com", "someone@somewhere.net", "torres@school.edu");
//
//        for(String testCase : testCases) {
//            person.setEmail(testCase);
//            performValidation();
//
//            assertEquals(0, bindingResult.getErrorCount());
//        }
//    }

    private void performValidation() {
        bindingResult = new BindException(address, "address");
        addressValidator.validate(address, bindingResult);
    }

    private Map<String, String> getErrors(String field, String expectedErrorCode) {
        Map<String, String> errors = new HashMap<>();

        performValidation();

        FieldError fieldError = bindingResult.getFieldError(field);
        errors.put("expectedError", messageSource.getMessage(expectedErrorCode, null, Locale.US));

        if(fieldError != null) {
            errors.put("actualError", messageSource.getMessage(Objects.requireNonNull(fieldError.getCode()), null, Locale.US));

            return errors;
        }

        errors.put("actualError", "There were no errors");

        return errors;
    }
}