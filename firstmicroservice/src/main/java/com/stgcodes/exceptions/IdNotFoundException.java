package com.stgcodes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "ID does not exist")
public class IdNotFoundException extends RuntimeException {

}
