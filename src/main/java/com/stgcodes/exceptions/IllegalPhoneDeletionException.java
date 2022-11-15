package com.stgcodes.exceptions;

public class IllegalPhoneDeletionException extends RuntimeException {
	
	public IllegalPhoneDeletionException() {
        super("User account must have at least one Phone");
    }
}
