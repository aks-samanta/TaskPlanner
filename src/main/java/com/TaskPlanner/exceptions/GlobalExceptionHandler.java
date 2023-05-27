package com.TaskPlanner.exceptions;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleAllExceptions(UserNotFoundException ex, WebRequest request) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

		problemDetail.setType(URI.create(request.getContextPath()));
		problemDetail.setInstance(URI.create(request.getContextPath()));

		return ResponseEntity.of(Optional.of(problemDetail));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetail> myAnyExpHandler(Exception anyException, WebRequest request) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
				anyException.getMessage());

		problemDetail.setType(URI.create(request.getContextPath()));
		problemDetail.setInstance(URI.create(request.getContextPath()));

		return ResponseEntity.of(Optional.of(problemDetail));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {

		Map<String, String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			response.put(field, message);
		});

		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}

	// to handle Not found exception
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ProblemDetail> mynotFoundHandler(NoHandlerFoundException nfe, WebRequest request) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, nfe.getMessage());

		problemDetail.setType(URI.create(request.getContextPath()));
		problemDetail.setInstance(URI.create(request.getContextPath()));

		return ResponseEntity.of(Optional.of(problemDetail));
	}

}
