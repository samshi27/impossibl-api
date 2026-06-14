package com.impossibl.api.exception;

public class IncompletePublishException extends RuntimeException {
	public IncompletePublishException(String message) {
		super(message);
	}
}