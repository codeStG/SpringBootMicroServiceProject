package com.stgcodes.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Person;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;

class PersonValidatorTests {

    private Person person;
    private PersonValidator validator;

    @BeforeEach
    void setUp() {
    	PhoneEntity testPhone = new PhoneEntity();
		testPhone.setPhoneNumber("123-12-1234");
		testPhone.setPhoneType(PhoneType.HOME);
		
        person = Person.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(List.of(testPhone))
                .build();

        validator = new PersonValidator();
    }


    @ParameterizedTest
    @ValueSource(strings = {"George", "Bobbie", "Somerandomguy", "Frederick", "Thisisanacceptableinput", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"})
    void testValidFirstName(String value) {
        person.setFirstName(value);
        
        assertDoesNotThrow(() -> validator.validate(person));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "G$%^&", "Ge0rge"})
    void testInvalidFirstName(String value) {
        person.setFirstName(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "name.format");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Flores", "Saskatchewan", "Somerandomlastname", "Areallyunnecesarilylonglastnamethatwillbetruncated"})
    void testValidLastName(String value) {
        person.setFirstName(value);
        
        assertDoesNotThrow(() -> validator.validate(person));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Fl0res", "$%^%&%&$%"})
    void testInvalidLastName(String value) {
        person.setLastName(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "name.format");
    }
    
    @Test
    void testLongLastNameIsTruncated() {
        person.setLastName("ThisIsAnExceptionallyLongLastName");
        validator.validate(person);
        
        assertEquals(25, person.getLastName().length(), "Something went wrong truncating the last name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"geflores", "username", "this_is.acceptable", "sixchar", "Uptotwentyfivecharacterss"})
    void testValidUsername(String value) {
        person.setUsername(value);
        
        assertDoesNotThrow(() -> validator.validate(person));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "       ", "\n", "\t", "Georg", "Up to twenty six characters"})
    void testInvalidUsername(String value) {
        person.setUsername(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "username.format");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-08-11", "1994-11-12", "1932-12-31", "2010-01-01"})
    void testValidDate(String value) {
        person.setDateOfBirth(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
        
        assertDoesNotThrow(() -> validator.validate(person));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3000-12-31", "2092-10-01", "3091-12-11", "4091-11-12"})
    void testFutureDateInvalid(String value) {
        person.setDateOfBirth(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "date.future");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123-45-6789", "234-56-7890", "883-02-4509"})
    void testValidSocialSecurity(String value) {
        person.setSocialSecurityNumber(value);
        
        assertDoesNotThrow(() -> validator.validate(person));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "123456789", "234567890", "000-00-0000", "999-99-9999"})
    void testInvalidSocialSecurity(String value) {
        person.setSocialSecurityNumber(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "ssn.format");
    }

    @ParameterizedTest
    @ValueSource(strings = {"MALE", "FEMALE", "REFUSE"})
    void testValidGender(String value) {
        person.setGender(Gender.valueOf(value));
        
        assertDoesNotThrow(() -> validator.validate(person));
    }
    
    @Test
    void testInvalidGender() {
        person.setGender(null);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "gender.invalid");
    }

    @ParameterizedTest
    @ValueSource(strings = {"someone@something.net", "aperson@thedomain.org", "what@who.net", "email@address.com"})
    void testValidEmail(String value) {
        person.setEmail(value);
        
        assertDoesNotThrow(() -> validator.validate(person));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "someone.net", "who@whatcom", "127.0.0.1"})
    void testInvalidEmail(String value) {
        person.setEmail(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "email.format");
    }

    @Test
    void testNoPhones() {
        person.setPhones(Collections.emptyList());
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(person), "Expected Person Validator to throw but it did not");
        assertEquals(ex.getErrors().getFieldError().getCode(), "phones.size");
    }
}