package com.stgcodes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Geographic State does not exist")
public class GeographicStateNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3510948887629727204L;

}
