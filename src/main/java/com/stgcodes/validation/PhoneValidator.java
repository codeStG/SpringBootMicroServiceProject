package com.stgcodes.validation;

import com.stgcodes.entity.UserEntity;
import com.stgcodes.exceptions.IllegalPhoneDeletionException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import static com.stgcodes.utils.constants.CustomMatchers.US_PHONE;

@Slf4j
@Component
public class PhoneValidator {

    public void validate(Phone phone) {
    	Errors errors = new BindException(phone, "phone");
    	
        validatePhoneNumber(phone.getPhoneNumber(), errors);
        ValidationUtils.rejectIfEmpty(errors, "phoneType", "phonetype.invalid");
        
        if(errors.hasErrors()) {
        	log.error(errors.toString());
            throw new InvalidRequestBodyException(Phone.class, errors);
        }
    }
    
    public void validateUserHasMorePhones(UserEntity userEntity) {
    	if(userEntity.getPhones().size() < 2) {
    		throw new IllegalPhoneDeletionException();
    	}
    }

    private void validatePhoneNumber(String phoneNumber, Errors errors) {
        if (!phoneNumber.matches(US_PHONE)) {
            errors.rejectValue("phoneNumber", "phonenumber.format");
        }
    }
}