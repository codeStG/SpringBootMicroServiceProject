package com.stgcodes.validation;

import com.stgcodes.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTests {

    private Person person;
    private ValidatorTestUtils validatorTestUtils;
    private BindingResult bindingResult;
    List<String> testCases;
    private Map<String, String> errors;

    @Before
    public void setUp() {
        person = Person.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth("08/13/1978")
                .socialSecurityNumber("123-45-6777")
                .gender("male")
                .email("brbyard@gmail.com")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new PersonValidator(), person);

        testCases = new ArrayList<>();
        errors = new HashMap<>();
    }

    @Test
    public void testEmptyFirstNameIsInvalid() {
        person.setFirstName("");
        errors = validatorTestUtils.getErrors("firstName", "name.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testFirstNameWithNonLetterCharacterIsInvalid() {
        testCases = Arrays.asList("G3orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setFirstName(testCase);
            errors = validatorTestUtils.getErrors("firstName", "name.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testFirstNameWithWhitespaceIsTrimmed() {
        testCases = Arrays.asList( "   George     ", "   G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setFirstName(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testLongFirstNameIsTruncated() {
        testCases = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12", "ABCDE\n   FGH\nIJKLMNOPQRSTU       VWXYZ]]]");

        for(String testCase : testCases) {
            person.setFirstName(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(25, person.getFirstName().length());
        }
    }

    @Test
    public void testEmptyLastNameIsInvalid() {
        person.setLastName("");
        errors = validatorTestUtils.getErrors("lastName", "name.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLastNameWithNonLetterCharacterIsInvalid() {
        testCases = Arrays.asList("G3orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setLastName(testCase);
            errors = validatorTestUtils.getErrors("lastName", "name.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testLastNameWithWhitespaceIsTrimmed() {
        testCases = Arrays.asList( "   George     ", "   G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setLastName(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testLongLastNameIsTruncated() {
        testCases = Arrays.asList( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12");

        for(String testCase : testCases) {
            person.setLastName(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(25, person.getLastName().length());
        }
    }

    @Test
    public void testWrongLengthUsernameIsInvalid() {
        testCases = Arrays.asList("  ", "   Georg", "\n\n\n\n    George w twenty six characters", "\n\n\n\n\n    ");

        for(String testCase : testCases) {
            person.setUsername(testCase);
            errors = validatorTestUtils.getErrors("username", "username.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testWrongDateFormatIsInvalid() {
        testCases = Arrays.asList("    ", "\n\n\n\n   ", "\n    08 sldi \n", "08-19-2022", "';[]'/.;'[", "08/32/2022");

        for(String testCase : testCases) {
            person.setDateOfBirth(testCase);
            errors = validatorTestUtils.getErrors("dateOfBirth", "date.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testFutureDateIsInvalid() {
        testCases = Arrays.asList("10/31/3200", "12/13/9600", "07/11/2096");

        for(String testCase : testCases) {
            person.setDateOfBirth(testCase);
            errors = validatorTestUtils.getErrors("dateOfBirth", "date.future");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testWrongSocialSecurityFormatIsInvalid() {
        testCases = Arrays.asList("000-00-0000", "999-99-9999", "      ", "\n\n\n\n\n      ", "03975395873250928559", "123456789");

        for(String testCase : testCases) {
            person.setSocialSecurityNumber(testCase);
            errors = validatorTestUtils.getErrors("socialSecurityNumber", "ssn.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidSocialSecurityNumbers() {
        testCases = Arrays.asList("845-67-8923", "682-67-4598", "523-91-4835", "401-83-2968");

        for(String testCase : testCases) {
            person.setSocialSecurityNumber(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testGenderOtherThanEnumIsInvalid() {
        testCases = Arrays.asList("man", "woman", "i dont know", "    ", "\n\n\n\n\n   sjdkhd", "\n\n\n");

        for(String testCase : testCases) {
            person.setGender(testCase);
            errors = validatorTestUtils.getErrors("gender", "gender.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidGender() {
        testCases = Arrays.asList("male", "female", "refuse", "mAle", "f e m     a    le", "r\ne\nf\nu\ns\ne");

        for(String testCase : testCases) {
            person.setGender(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testWrongEmailFormatIsInvalid() {
        testCases = Arrays.asList("getorres@teksystems", "getorresteksystems.com", "georgie@localhost", "georgie@127.0.0.1");

        for(String testCase : testCases) {
            person.setEmail(testCase);
            errors = validatorTestUtils.getErrors("email", "email.format");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testValidEmail() {
        testCases = Arrays.asList("getorres@teksystems.com", "who@what.org", "me@gmail.com", "someone@somewhere.net", "torres@school.edu");

        for(String testCase : testCases) {
            person.setEmail(testCase);
            bindingResult = validatorTestUtils.performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }
}