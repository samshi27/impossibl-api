package com.impossibl.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(PostNotFoundException.class)
	public ProblemDetail handleNotFound(PostNotFoundException ex) {
		log.error("Unexpected error", ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.NOT_FOUND, ex.getMessage());
		problem.setTitle("Post Not Found");
		return problem;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
		log.error("Unexpected error", ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_REQUEST, "Validation failed");
		problem.setTitle("Invalid Request");

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage()));
		problem.setProperty("errors", errors);

		return problem;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ProblemDetail handleConflict(DataIntegrityViolationException ex) {
		log.error("Unexpected error", ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.CONFLICT, "A record with these details already exists.");
		problem.setTitle("Conflict");
		return problem;
	}

	@ExceptionHandler(DuplicateSlugException.class)
	public ProblemDetail handleDuplicateSlug(DuplicateSlugException ex) {
		log.error("Unexpected error", ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.CONFLICT, ex.getMessage());
		problem.setTitle("Duplicate Slug");
		return problem;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ProblemDetail handleUnreadable(HttpMessageNotReadableException ex) {
		log.error("Unexpected error", ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_REQUEST, "Malformed request body or invalid field value.");
		problem.setTitle("Invalid Request Body");
		return problem;
	}

	@ExceptionHandler(IncompletePublishException.class)
	public ProblemDetail handleIncompletePublish(IncompletePublishException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_REQUEST, ex.getMessage());
		problem.setTitle("Incomplete Publish");
		return problem;
	}

	@ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
	public ProblemDetail handleBadCredentials(
			org.springframework.security.authentication.BadCredentialsException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.UNAUTHORIZED, "Invalid username or password.");
		problem.setTitle("Authentication Failed");
		return problem;
	}
}