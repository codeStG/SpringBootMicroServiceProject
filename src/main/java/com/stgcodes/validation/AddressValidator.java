package com.stgcodes.validation;

import com.stgcodes.model.Address;
import com.stgcodes.utils.FieldFormatter;
import com.stgcodes.validation.enums.GeographicState;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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
        validateState(address.getState(), errors);
        validateZip(address.getZip(), errors);
    }

    private void validateLineOne(String lineOne, Errors errors) {
        if (lengthIsInvalid(1, 75, lineOne)) {
            errors.rejectValue("lineOne", "lineone.format");
        }
    }

    private void validateLineTwo(String lineTwo, Errors errors) {
        if (lengthIsInvalid(1, 25, lineTwo)) {
            errors.rejectValue("lineTwo", "linetwo.format");
        }
    }

    private void validateCity(String city, Errors errors) {
        if (lengthIsInvalid(1, 75, city)) {
            errors.rejectValue("city", "city.format");
        }
    }

    private void validateState(String state, Errors errors) {
        FieldFormatter fieldFormatter = new FieldFormatter();

        if(!GeographicState.isAState(fieldFormatter.formatAsEnum(state))) {
            errors.rejectValue("state", "state.invalid");
        }
    }

    private void validateZip(String zip, Errors errors) {
        if (!zip.matches(US_ZIP_CODE)) {
            errors.rejectValue("zip", "zip.format");
        }
    }

    private boolean lengthIsInvalid(int min, int max, String value) {
        return value.length() < min || value.length() > max;
    }
}