package com.stgcodes.validation;

import com.stgcodes.model.Phone;
import com.stgcodes.validation.enums.PhoneType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.stgcodes.utils.constants.CustomMatchers.US_PHONE;

@Component
public class PhoneValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Phone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Phone phone = (Phone) target;

        validatePhoneNumber(phone.getPhoneNumber(), errors);
        ValidationUtils.rejectIfEmpty(errors, "phoneType", "phonetype.invalid");
    }

    private void validatePhoneNumber(String phoneNumber, Errors errors) {
        if (!phoneNumber.matches(US_PHONE)) {
            errors.rejectValue("phoneNumber", "phonenumber.format");
        }
    }
}