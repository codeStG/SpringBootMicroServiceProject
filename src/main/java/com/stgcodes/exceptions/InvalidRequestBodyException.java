package com.stgcodes.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

public class InvalidRequestBodyException extends RuntimeException {

    private final transient Errors errors;

    public InvalidRequestBodyException(Class<?> clazz, Errors errors) {
        super(generateMessage(clazz.getSimpleName(), errors.getErrorCount()));
        this.errors = errors;
    }

    private static String generateMessage(String entity, int errorCount) {
        return "There were " + errorCount + " error(s) saving the " + StringUtils.capitalize(entity);
    }

    public Errors getErrors() {
        return errors;
    }
}
