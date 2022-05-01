package com.booking.Exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidMealPreferenceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidMealPreferenceException(String message) {
		super(message);
	}

}
