package com.impossibl.api.exception;

public class DuplicateSlugException extends RuntimeException {
	public DuplicateSlugException(String slug) {
		super("A post with the slug '" + slug + "' already exists.");
	}
}
