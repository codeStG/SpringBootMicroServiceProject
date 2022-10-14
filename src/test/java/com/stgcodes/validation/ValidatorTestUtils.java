package com.stgcodes.validation;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.*;

import java.util.*;

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

    public List<String> getErrors() {
        Errors errors = new BeanPropertyBindingResult(target, String.valueOf(target));
        validator.validate(target, errors);

        List<String> errorMessages = new ArrayList<>();

        if(errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> errorMessages.add(messageSource.getMessage(e.getCode(), null, Locale.US)));
        } else {
            errorMessages.add(messageSource.getMessage("errors.none", null, Locale.US));
        }

        return errorMessages;
    }
}
