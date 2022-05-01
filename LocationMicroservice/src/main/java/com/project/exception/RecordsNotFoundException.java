package com.project.exception;

public class RecordsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordsNotFoundException(String message) {
		super(message);
	}
}
