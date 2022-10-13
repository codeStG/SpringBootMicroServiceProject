package com.stgcodes.validation;

import com.stgcodes.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTests {

    private Person person;
    private ValidatorTestUtils validatorTestUtils;
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
        person.setFirstName("G3orge");
        errors = validatorTestUtils.getErrors("firstName", "name.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLongFirstNameIsTruncatedToMakeValid() {
        person.setFirstName("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        errors = validatorTestUtils.getErrors("firstName", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyLastNameIsInvalid() {
        person.setLastName("");
        errors = validatorTestUtils.getErrors("lastName", "name.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLastNameWithNonLetterCharacterIsInvalid() {
        person.setLastName("G3orge");
        errors = validatorTestUtils.getErrors("lastName", "name.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLongLastNameIsTruncated() {
        person.setLastName("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        errors = validatorTestUtils.getErrors("lastName", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testUsernameTooLong() {
        person.setUsername("George but make it too long");
        errors = validatorTestUtils.getErrors("username", "username.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testUsernameTooShort() {
        person.setUsername("Georg");
        errors = validatorTestUtils.getErrors("username", "username.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidDate() {
        person.setDateOfBirth("08/30/2022");
        errors = validatorTestUtils.getErrors("dateOfBirth", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmptyDate() {
        person.setDateOfBirth("");
        errors = validatorTestUtils.getErrors("dateOfBirth", "date.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testInvalidDateFormat() {
        String testCase = "8 29 22";

        person.setDateOfBirth(testCase);
        errors = validatorTestUtils.getErrors("dateOfBirth", "date.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testMonthOutOfBounds() {
        person.setDateOfBirth("13/13/1995");
        errors = validatorTestUtils.getErrors("dateOfBirth", "date.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testDayOfMonthOutOfBounds() {
        person.setDateOfBirth("08/32/2022");
        errors = validatorTestUtils.getErrors("dateOfBirth", "date.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testFutureDateIsInvalid() {
        person.setDateOfBirth("10/31/3200");
        errors = validatorTestUtils.getErrors("dateOfBirth", "date.future");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidSocialSecurity() {
        person.setSocialSecurityNumber("123-45-6789");
        errors = validatorTestUtils.getErrors("socialSecurityNumber", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testInvalidSocialSecurityFormat() {
        person.setSocialSecurityNumber("123456789");
        errors = validatorTestUtils.getErrors("socialSecurityNumber", "ssn.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidGender() {
        person.setGender("MALE");
        errors = validatorTestUtils.getErrors("gender", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testInvalidGender() {
        person.setGender("Man");
        errors = validatorTestUtils.getErrors("gender", "gender.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmailWithoutAtSymbolInvalid() {
        person.setEmail("who.com");
        errors = validatorTestUtils.getErrors("email", "email.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testEmailWithoutTopLevelDomainIsInvalid() {
        person.setEmail("who@what");
        errors = validatorTestUtils.getErrors("email", "email.format");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testValidEmail() {
        person.setEmail("who@what.com");
        errors = validatorTestUtils.getErrors("email", "errors.none");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }
}