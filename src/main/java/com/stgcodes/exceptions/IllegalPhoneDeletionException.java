package com.stgcodes.exceptions;

public class IllegalPhoneDeletionException extends RuntimeException {
	
	private static final long serialVersionUID = -2031080788219946141L;

	public IllegalPhoneDeletionException() {
        super("User account must have at least one Phone");
    }
}
