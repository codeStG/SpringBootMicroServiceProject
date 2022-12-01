package com.stgcodes.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

public class InvalidRequestBodyException extends RuntimeException {

	private static final long serialVersionUID = -6668944283553004365L;
	private final transient Errors errors;

    public InvalidRequestBodyException(Class<?> clazz, Errors errors) {
        super(generateMessage(clazz.getSimpleName(), errors.getErrorCount()));
        this.errors = errors;
    }

    private static String generateMessage(String entity, int errorCount) {
        return "Found " + errorCount + " error(s) in the parameters provided for " + StringUtils.capitalize(entity);
    }

    public Errors getErrors() {
        return errors;
    }
}
