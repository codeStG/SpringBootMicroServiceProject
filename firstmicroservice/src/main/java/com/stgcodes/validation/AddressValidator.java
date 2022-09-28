package com.stgcodes.validation;

import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.stgcodes.utils.constants.CustomMatchers.WHITESPACE_DASH_SLASH_MATCHER;

@Component
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Address address = (Address) target;

        validateLineOne(address, errors);
        validateLineTwo(address, errors);
        validateCity(address, errors);
        validateState(address, errors);
        validateZip(address, errors);
    }

    private void validateLineOne(Address address, Errors errors) {
        String lineOne = cleanString(address.getLineOne());

        if (lengthIsInvalid(1, 50, lineOne)) {
            errors.rejectValue("lineOne", "lineone.format");
        }
    }

    private void validateLineTwo(Address address, Errors errors) {
        String lineTwo = cleanString(address.getLineTwo());

        if (lengthIsInvalid(1, 25, lineTwo)) {
            errors.rejectValue("lineTwo", "linetwo.format");
        }
    }

    private void validateCity(Address address, Errors errors) {
        String city = cleanString(address.getCity());

        if (lengthIsInvalid(1, 50, city)) {
            errors.rejectValue("city", "city.format");
        }
    }

    private void validateState(Address address, Errors errors) {
        String state = address.getState();

        if(!GeographicState.isAState(state)) {
            errors.rejectValue("state", "state.invalid");
        }
    }

    private void validateZip(Address address, Errors errors) {
        String zip = cleanString(address.getZip());

        if (zip.length() != 5 && zip.length() != 9) {
            errors.rejectValue("zip", "zip.format");
        }
    }

    private String cleanString(String str) {
        return str.replaceAll(WHITESPACE_DASH_SLASH_MATCHER, "");
    }

    private boolean lengthIsInvalid(int min, int max, String value) {
        return value.length() < min || value.length() > max;
    }
}