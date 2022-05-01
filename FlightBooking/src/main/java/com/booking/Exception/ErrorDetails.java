package com.booking.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorDetails {

	private String errorMessage;
	private HttpStatus status;
	private LocalDateTime datetime;
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	
	
	public ErrorDetails(String errorMessage, HttpStatus status, LocalDateTime datetime) {
		super();
		this.errorMessage = errorMessage;
		this.status = status;
		this.datetime = datetime;
	}


	public ErrorDetails() {
		super();
	}
	
	
}
