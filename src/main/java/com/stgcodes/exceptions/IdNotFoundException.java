package com.stgcodes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "ID does not exist")
public class IdNotFoundException extends RuntimeException {

}
