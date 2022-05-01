package com.project.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExcpetion extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorDetails> idNotFoundException(IdNotFoundException ex) {
		ErrorDetails response = new ErrorDetails();
		response.setErrorcode("ID NOT FOUND");
		response.setErrormsg(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RecordsNotFoundException.class)
	public ResponseEntity<ErrorDetails> idNotFoundException(RecordsNotFoundException ex) {
		ErrorDetails response = new ErrorDetails();
		response.setErrorcode("NO RECORDS");
		response.setErrormsg(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ErrorDetails> invalidUserException(InvalidUserException ex) {
		ErrorDetails response = new ErrorDetails(
					ex.getMessage(),
					"Invalid User",
					LocalDateTime.now()
				);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldname = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldname, message);

		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
