package com.stgcodes.validation;

import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AddressValidatorTests {

    private Address address;
    private AddressValidator validator;

    @BeforeEach
    public void setUp() {
        address = Address.builder()
                .lineOne("5678 What St.")
                .lineTwo("Unit 8")
                .city("Atlanta")
                .state(GeographicState.GA)
                .zip("78019")
                .build();

        validator = new AddressValidator();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"1234 David St.", "111 Montgomery Rd.", "P.O. Box 893", "This is acceptable", "   384 Richard Ave.   "})
    void testValidLineOne(String value) {
        address.setLineOne(value);
        
        assertDoesNotThrow(() -> validator.validate(address));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Some Random Line One That Does Not Exist With A Bunch Of Lettersssssssssssss"})
    void testInvalidLineOne(String value) {
        address.setLineOne(value);

        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(address), "Expected Address Validator to throw but it did not");
        assertEquals("lineone.format", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "Unit 4", "Apt. 3", "No. 1", "   Unit 189   ", "This is acceptable input"})
    void testValidLineTwo(String value) {
        address.setLineTwo(value);
        
        assertDoesNotThrow(() -> validator.validate(address));
    }
    
    @Test
    void testInvalidLineTwo() {
        address.setLineTwo("abcdefghijklmnopqrstuvwxyz");
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(address), "Expected Address Validator to throw but it did not");
        assertEquals("linetwo.format", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Austin", "New York City", "Raleigh", "This is acceptable", "Miami"})
    void testValidCity(String value) {
        address.setCity(value);
        
        assertDoesNotThrow(() -> validator.validate(address));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "Village of Grosse Pointe Shores City, A Michigan City Plussssssssssssssssssssssss"})
    void testEmptyCityIsInvalid(String value) {
        address.setCity(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(address), "Expected Address Validator to throw but it did not");
        assertEquals("city.format", ex.getErrors().getFieldError().getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "76017", "75052", "89013", "54321"})
    void testValid5DigitZip(String value) {
        address.setZip(value);
        
        assertDoesNotThrow(() -> validator.validate(address));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345-6789", "76017-1234", "33101-0119", "27513-1100", "11385-9994"})
    void testValid9DigitZip(String value) {
        address.setZip(value);
        
        assertDoesNotThrow(() -> validator.validate(address));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "7601", "12345-67890", "12345-678", "12345-6", "12345-", "12345-67", "1", "12", "123"})
    void testInvalidZip(String value) {
        address.setZip(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(address), "Expected Address Validator to throw but it did not");
        assertEquals("zip.format", ex.getErrors().getFieldError().getCode());
    }
}