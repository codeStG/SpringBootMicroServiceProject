package com.stgcodes.validation;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

//Look into @PropertySource()
public class ValidatorTestUtils {

    private final Validator validator;
    private final Object target;

    private final ResourceBundleMessageSource messageSource;

    public ValidatorTestUtils(Validator validator, Object target) {
        this.validator = validator;
        this.target = target;
        this.messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    public Map<String, String> getErrors(String field, String expectedErrorCode) {
        Map<String, String> errors = new HashMap<>();

        //Change this to Error interface
        BindingResult bindingResult = new BindException(target, String.valueOf(target));
        validator.validate(target, bindingResult);

        FieldError fieldError = bindingResult.getFieldError(field);
        errors.put("expectedError", messageSource.getMessage(expectedErrorCode, null, Locale.US));

        if(fieldError != null) {
            errors.put("actualError", messageSource.getMessage(Objects.requireNonNull(fieldError.getCode()), null, Locale.US));

            return errors;
        }

        errors.put("actualError", "There were no errors");

        return errors;
    }
}
