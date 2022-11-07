package com.stgcodes.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

public class InvalidRequestBodyException extends RuntimeException {

    private final BindingResult bindingResult;

    public InvalidRequestBodyException(Class<?> clazz, BindingResult bindingResult) {
        super(generateMessage(clazz.getSimpleName(), bindingResult.getErrorCount()));
        this.bindingResult = bindingResult;
    }

    private static String generateMessage(String entity, int errorCount) {
        return "There were " + errorCount + " error(s) saving the " + StringUtils.capitalize(entity);
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
