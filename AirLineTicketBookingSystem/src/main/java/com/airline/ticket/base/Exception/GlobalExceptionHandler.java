package com.airline.ticket.base.Exception;

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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ErrorDetails> EmailNotFoundException(EmailNotFoundException ex) {
		ErrorDetails response = new ErrorDetails();
		response.setErrorcode("EMAIL NOT FOUND");
		response.setErrormsg(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidEmailFormatException.class)
	public ResponseEntity<ErrorDetails> InvalidEmailFormatExceptionHandler(InvalidEmailFormatException ex) {
		ErrorDetails error = new ErrorDetails(ex.getMessage(), "Email Invalid", LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldname = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldname, message);

		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

}