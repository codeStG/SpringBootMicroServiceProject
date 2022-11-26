package com.stgcodes.validation;

import static com.stgcodes.utils.constants.CustomMatchers.US_ZIP_CODE;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Address;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AddressValidator {

	public void validate(Address address) {
    	Errors errors = new BindException(address, "address");
		
        validateLineOne(address.getLineOne(), errors);
        validateLineTwo(address.getLineTwo(), errors);
        validateCity(address.getCity(), errors);
        ValidationUtils.rejectIfEmpty(errors, "state", "state.invalid");
        validateZip(address.getZip(), errors);
        
        if(errors.hasErrors()) {
            log.error(errors.toString());
            throw new InvalidRequestBodyException(Address.class, errors);
        }
	}

    private void validateLineOne(String lineOne, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lineOne", "lineone.format");

        if (lineOne.length() > 75) {
            errors.rejectValue("lineOne", "lineone.format");
        }
    }

    private void validateLineTwo(String lineTwo, Errors errors) {
        if (lineTwo.length() > 25) {
            errors.rejectValue("lineTwo", "linetwo.format");
        }
    }

    private void validateCity(String city, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "city.format");

        if (city.length() > 75) {
            errors.rejectValue("city", "city.format");
        }
    }

    private void validateZip(String zip, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", "zip.format");

        if (!zip.matches(US_ZIP_CODE)) {
            errors.rejectValue("zip", "zip.format");
        }
    }
}