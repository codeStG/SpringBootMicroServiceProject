package com.stgcodes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "User account must have at least one Phone")
public class IllegalPhoneDeletionException extends RuntimeException {

}
