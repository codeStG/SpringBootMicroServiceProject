package com.stgcodes.validation;

import com.stgcodes.model.Address;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.stgcodes.utils.constants.CustomMatchers.US_ZIP_CODE;

@Component
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Address address = (Address) target;

        validateLineOne(address.getLineOne(), errors);
        validateLineTwo(address.getLineTwo(), errors);
        validateCity(address.getCity(), errors);
        ValidationUtils.rejectIfEmpty(errors, "state", "state.invalid");
        validateZip(address.getZip(), errors);
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