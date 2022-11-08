package com.stgcodes.validation;

import com.stgcodes.model.Person;
import com.stgcodes.validation.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonValidatorTests {

    private Person person;
    private ValidatorTestUtils validatorTestUtils;
    private ResourceBundleMessageSource messageSource;
    private List<String> errors;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .build();

        validatorTestUtils = new ValidatorTestUtils(new PersonValidator(), person);
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    @ParameterizedTest
    @ValueSource(strings = {"George", "Bobbie", "Somerandomguy", "Frederick", "Thisisanacceptableinput", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"})
    void testValidFirstName(String value) {
        person.setFirstName(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "G$%^&", "Ge0rge"})
    void testInvalidFirstName(String value) {
        person.setFirstName(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("name.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Flores", "Saskatchewan", "Somerandomlastname", "Areallyunnecesarilylonglastnamethatwillbetruncated"})
    void testValidLastName(String value) {
        person.setFirstName(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Fl0res", "$%^%&%&$%"})
    void testInvalidLastName(String value) {
        person.setLastName(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("name.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"geflores", "username", "this_is.acceptable", "sixchar", "Uptotwentyfivecharacterss"})
    void testValidUsername(String value) {
        person.setUsername(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "       ", "\n", "\t", "Georg", "Up to twenty six characters"})
    void testInvalidUsername(String value) {
        person.setUsername(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("username.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-08-11", "1994-11-12", "1932-12-31", "2010-01-01"})
    void testValidDate(String value) {
        person.setDateOfBirth(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3000-12-31", "2092-10-01", "3091-12-11", "4091-11-12"})
    void testFutureDateInvalid(String value) {
        person.setDateOfBirth(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("date.future", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123-45-6789", "234-56-7890", "883-02-4509"})
    void testValidSocialSecurity(String value) {
        person.setSocialSecurityNumber(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "123456789", "234567890", "000-00-0000", "999-99-9999"})
    void testInvalidSocialSecurity(String value) {
        person.setSocialSecurityNumber(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("ssn.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MALE", "FEMALE", "REFUSE"})
    void testValidGender(String value) {
        person.setGender(Gender.valueOf(value));
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"someone@something.net", "aperson@thedomain.org", "what@who.net", "email@address.com"})
    void testValidEmail(String value) {
        person.setEmail(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("errors.none", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "someone.net", "who@whatcom", "127.0.0.1"})
    void testInvalidEmail(String value) {
        person.setEmail(value);
        errors = validatorTestUtils.getErrors();

        String expected = messageSource.getMessage("email.format", null, Locale.US);

        assertEquals(expected, errors.get(0));
    }
}