package com.stgcodes.validation;

import com.stgcodes.model.Address;
import com.stgcodes.validation.enums.GeographicState;
import org.apache.commons.lang3.StringUtils;
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

        if (lineOne.length() > 50 || lineOne.length() == 0) {
            errors.rejectValue("lineOne", "lineone.format");
        }
    }

    private void validateLineTwo(Address address, Errors errors) {
        String lineTwo = cleanString(address.getLineTwo());

        if (lineTwo.length() > 25 || lineTwo.length() == 0) {
            errors.rejectValue("lineTwo", "linetwo.format");
        }
    }

    private void validateCity(Address address, Errors errors) {
        String city = cleanString(address.getCity());

        if (city.length() > 21 || city.length() == 0) {
            errors.rejectValue("city", "city.format");
        }
    }

    private void validateState(Address address, Errors errors) {
        String state = address.getState().trim();

        if(GeographicState.valueOfAbbreviation(state) == null && GeographicState.valueOfName(state) == null) {
            errors.rejectValue("state", "state.invalid");
        }
    }

    //TODO: Check for dashes and remove them if present
    private void validateZip(Address address, Errors errors) {
        String zip = cleanString(address.getZip());

        if (zip.length() != 5 && zip.length() != 9) {
            errors.rejectValue("zip", "zip.format");
        }
    }

    private String cleanString(String str) {
        return str.replaceAll(WHITESPACE_DASH_SLASH_MATCHER, StringUtils.EMPTY);
    }
}
