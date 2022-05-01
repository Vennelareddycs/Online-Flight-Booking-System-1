package com.booking.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleDataNotFound(DataNotFoundException e) {
		ErrorDetails err = new ErrorDetails(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoSuchBookingIdException.class)
	public ResponseEntity<ErrorDetails> handleNoSuchBookingId(NoSuchBookingIdException e) {
		ErrorDetails err = new ErrorDetails(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoSuchPassengerIdException.class)
	public ResponseEntity<ErrorDetails> handleNoSuchPassengerId(NoSuchPassengerIdException e) {
		ErrorDetails err = new ErrorDetails(e.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ErrorDetails> handleInvalidUser(InvalidUserException ex){
		ErrorDetails error = new ErrorDetails(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND, 
				LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidPassportException.class)
	public ResponseEntity<ErrorDetails> handleInvalidPassport(InvalidPassportException ex){
		ErrorDetails error = new ErrorDetails(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND, 
				LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidMealPreferenceException.class)
	public ResponseEntity<ErrorDetails> handleInvalidMealPreference(InvalidMealPreferenceException ex){
		ErrorDetails error = new ErrorDetails(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND, 
				LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidDateFormatException.class)
	public ResponseEntity<ErrorDetails> handleInvalidMealPreference(InvalidDateFormatException ex){
		ErrorDetails error = new ErrorDetails(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND, 
				LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
}
