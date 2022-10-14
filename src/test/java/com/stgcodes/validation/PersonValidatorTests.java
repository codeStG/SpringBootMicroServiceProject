package com.stgcodes.validation;

import com.stgcodes.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTests {

    private Person person;
    private ValidatorTestUtils validatorTestUtils;
    private ResourceBundleMessageSource messageSource;
    private List<String> errors;

    @Before
    public void setUp() {
        person = Person.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth("08/13/1978")
                .socialSecurityNumber("123-45-6777")
                .gender("MALE")
                .email("brbyard@gmail.com")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new PersonValidator(), person);
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    @Test
    public void testValidFirstName() {
        person.setFirstName("George");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testLongFirstNameIsTruncatedToMakeValid() {
        person.setFirstName("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyFirstNameIsInvalid() {
        person.setFirstName("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("name.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testFirstNameWithNonLetterCharacterIsInvalid() {
        person.setFirstName("G3orge");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("name.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidLastName() {
        person.setFirstName("Flores");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testLongLastNameIsTruncatedToMakeValid() {
        person.setLastName("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyLastNameIsInvalid() {
        person.setLastName("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("name.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testLastNameWithNonLetterCharacterIsInvalid() {
        person.setLastName("Flor3s");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("name.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidUsername() {
        person.setUsername("Georgieboy");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testUsernameTooLong() {
        person.setUsername("George but make it too long");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("username.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testUsernameTooShort() {
        person.setUsername("Georg");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("username.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidDate() {
        person.setDateOfBirth("08/30/2022");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmptyDate() {
        person.setDateOfBirth("");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("date.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testInvalidDateFormat() {
        person.setDateOfBirth("8 29 22");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("date.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testMonthOutOfBounds() {
        person.setDateOfBirth("13/13/1995");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("date.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testDayOfMonthOutOfBounds() {
        person.setDateOfBirth("08/32/2022");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("date.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testFutureDateIsInvalid() {
        person.setDateOfBirth("10/31/3200");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("date.future", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidSocialSecurity() {
        person.setSocialSecurityNumber("123-45-6789");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testInvalidSocialSecurityFormat() {
        person.setSocialSecurityNumber("123456789");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("ssn.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidGender() {
        person.setGender("MALE");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testInvalidGender() {
        person.setGender("Man");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("gender.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testValidEmail() {
        person.setEmail("who@what.com");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmailWithoutAtSymbolInvalid() {
        person.setEmail("who.com");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("email.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @Test
    public void testEmailWithoutTopLevelDomainIsInvalid() {
        person.setEmail("who@what");
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("email.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
}