package com.stgcodes.exceptions;

import org.apache.commons.lang3.StringUtils;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1425261801890555925L;

	public IdNotFoundException(Class<?> clazz, String id) {
        super(generateMessage(clazz.getSimpleName(), id));
    }

    private static String generateMessage(String entity, String value) {
        return StringUtils.capitalize(entity) +
                " was not found with ID " +
                value;
    }
}