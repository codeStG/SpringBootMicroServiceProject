package com.stgcodes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidRequestBodyException extends RuntimeException {

    public InvalidRequestBodyException(String message) {
        super(message);
    }

    public InvalidRequestBodyException() {}
}
