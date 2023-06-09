package com.stgcodes.validation;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.User;
import com.stgcodes.validation.enums.Gender;
import com.stgcodes.validation.enums.PhoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTests {

    private User user;
    private UserValidator validator;

    @BeforeEach
    void setUp() {
    	PhoneEntity testPhone = new PhoneEntity();
		testPhone.setPhoneNumber("123-12-1234");
		testPhone.setPhoneType(PhoneType.HOME);
		
        user = User.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(List.of(testPhone))
                .build();

        validator = new UserValidator();
    }


    @ParameterizedTest
    @ValueSource(strings = {"George", "Bobbie", "Somerandomguy", "Frederick", "Thisisanacceptableinput", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"})
    void testValidFirstName(String value) {
        user.setFirstName(value);
        
        assertDoesNotThrow(() -> validator.validate(user));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "G$%^&", "Ge0rge"})
    void testInvalidFirstName(String value) {
        user.setFirstName(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("name.format", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Flores", "Saskatchewan", "Somerandomlastname", "Areallyunnecesarilylonglastnamethatwillbetruncated"})
    void testValidLastName(String value) {
        user.setFirstName(value);
        
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Fl0res", "$%^%&%&$%"})
    void testInvalidLastName(String value) {
        user.setLastName(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("name.format", ex.getErrors().getFieldError().getCode());
    }
    
    @Test
    void testLongLastNameIsTruncated() {
        user.setLastName("ThisIsAnExceptionallyLongLastName");
        validator.validate(user);
        
        assertEquals(25, user.getLastName().length(), "Something went wrong truncating the last name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"geflores", "username", "this_is.acceptable", "sixchar", "Uptotwentyfivecharacterss"})
    void testValidUsername(String value) {
        user.setUsername(value);
        
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "       ", "\n", "\t", "Georg", "Up to twenty six characters"})
    void testInvalidUsername(String value) {
        user.setUsername(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("username.format", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-08-11", "1994-11-12", "1932-12-31", "2010-01-01"})
    void testValidDate(String value) {
        user.setDateOfBirth(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
        
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3000-12-31", "2092-10-01", "3091-12-11", "4091-11-12"})
    void testFutureDateInvalid(String value) {
        user.setDateOfBirth(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("date.future", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123-45-6789", "234-56-7890", "883-02-4509"})
    void testValidSocialSecurity(String value) {
        user.setSocialSecurityNumber(value);
        
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "123456789", "234567890", "000-00-0000", "999-99-9999"})
    void testInvalidSocialSecurity(String value) {
        user.setSocialSecurityNumber(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("ssn.format", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"MALE", "FEMALE", "REFUSE"})
    void testValidGender(String value) {
        user.setGender(Gender.valueOf(value));
        
        assertDoesNotThrow(() -> validator.validate(user));
    }
    
    @Test
    void testInvalidGender() {
        user.setGender(null);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("gender.invalid", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"someone@something.net", "auser@thedomain.org", "what@who.net", "email@address.com"})
    void testValidEmail(String value) {
        user.setEmail(value);
        
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "someone.net", "who@whatcom", "127.0.0.1"})
    void testInvalidEmail(String value) {
        user.setEmail(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("email.format", ex.getErrors().getFieldError().getCode());
    }

    @Test
    void testNoPhones() {
        user.setPhones(Collections.emptyList());
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(user), "Expected User Validator to throw but it did not");
        assertEquals("phones.size", ex.getErrors().getFieldError().getCode());
    }
}