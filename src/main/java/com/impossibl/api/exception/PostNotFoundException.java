package com.impossibl.api.exception;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String slug) {
		super("Post not found with slug: " + slug);
	}

	public PostNotFoundException(Long id) {
		super("Post not found with id: " + id);
	}
}