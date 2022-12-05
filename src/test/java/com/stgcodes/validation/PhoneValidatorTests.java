package com.stgcodes.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.stgcodes.entity.PersonEntity;
import com.stgcodes.exceptions.IllegalPhoneDeletionException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.mappers.PersonMapper;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Person;
import com.stgcodes.model.Phone;
import com.stgcodes.validation.enums.PhoneType;


class PhoneValidatorTests {

    private Phone phone;
    private PersonEntity personEntity;
    private PhoneValidator validator;

    @BeforeEach
    public void setUp() {
        phone = Phone.builder()
        		.phoneId(0L)
                .phoneNumber("223-456-7890")
                .phoneType(PhoneType.MOBILE)
                .build();
        
        personEntity = PersonMapper.INSTANCE.personToPersonEntity(
        		Person.builder()
        		.personId(0L)
        		.phones(List.of(PhoneMapper.INSTANCE.phoneToPhoneEntity(phone)))
        		.build());
        
        phone.setPersonEntity(personEntity);

        validator = new PhoneValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"806-123-4567", "214-123-4567", "817-902-5478", "234-567-8901", "223-456-7890"})
    void testValidPhoneNumber(String value) {
        phone.setPhoneNumber(value);
        
        assertDoesNotThrow(() -> validator.validate(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t", "223-456-78901", "1234567890", "(806) 765 1234"})
    void testInvalidPhoneNumber(String value) {
        phone.setPhoneNumber(value);
        
        InvalidRequestBodyException ex =  
        		assertThrows(InvalidRequestBodyException.class, () -> validator.validate(phone), "Expected Phone Validator to throw but it did not");
        assertEquals("phonenumber.format", ex.getErrors().getFieldError().getCode());
    }
    
    @Test
    void testUserHasMoreThanOnePhoneOnDelete() {
    	personEntity.addPhone(PhoneMapper.INSTANCE.phoneToPhoneEntity(Phone.builder().build()));
    	
    	assertDoesNotThrow(() -> validator.validateUserHasMorePhones(personEntity));
    }
    
    @Test
    void testUserHasOnePhoneOnDelete() {    	
    	IllegalPhoneDeletionException ex =  
        		assertThrows(IllegalPhoneDeletionException.class, () -> validator.validateUserHasMorePhones(personEntity), "Expected Phone Validator to throw but it did not");
        assertEquals("User account must have at least one Phone", ex.getMessage());
    }
}