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

    private Validator validator;
    private Object target;

    //Change this to Error interface
    private BindingResult bindingResult;
    private ResourceBundleMessageSource messageSource;

    public ValidatorTestUtils(Validator validator, Object target) {
        this.validator = validator;
        this.target = target;
        this.messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    public BindingResult performValidation() {
        bindingResult = new BindException(target, String.valueOf(target));
        validator.validate(target, bindingResult);

        return  bindingResult;
    }

    public Map<String, String> getErrors(String field, String expectedErrorCode) {
        Map<String, String> errors = new HashMap<>();

        performValidation();

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
