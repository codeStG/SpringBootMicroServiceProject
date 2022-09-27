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
        String lineOne = address.getLineOne().replaceAll(WHITESPACE_DASH_SLASH_MATCHER, "");

        if (lineOne.length() > 50 || lineOne.length() == 0) {
            errors.rejectValue("lineOne", "lineone.format");
        }
    }

    private void validateLineTwo(Address address, Errors errors) {
        String lineTwo = address.getLineTwo().replaceAll(WHITESPACE_DASH_SLASH_MATCHER, "");

        if (lineTwo.length() > 25 || lineTwo.length() == 0) {
            errors.rejectValue("lineTwo", "linetwo.format");
        }
    }

    private void validateCity(Address address, Errors errors) {
        String city = address.getCity().replaceAll(WHITESPACE_DASH_SLASH_MATCHER, "");

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
        String zip = address.getZip().replaceAll(WHITESPACE_DASH_SLASH_MATCHER, "");

        if (zip.length() != 5 && zip.length() != 9) {
            errors.rejectValue("zip", "zip.format");
        }
    }
}
