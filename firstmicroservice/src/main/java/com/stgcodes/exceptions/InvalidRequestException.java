package com.stgcodes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Request provided contained invalid parameters")
public class InvalidRequestException extends RuntimeException {

}
