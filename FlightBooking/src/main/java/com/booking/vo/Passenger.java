package com.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
	
	private int passengerId;
	private String firstName;
	private String lastName;
	private String passportNo;
	private String mealPref;
	private String flightBooked;
	
	public Passenger(
			String firstName, 
			String lastName, 
			String passportNo, 
			String mealPref,
			String flightBooked) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.passportNo = passportNo;
		this.mealPref = mealPref;
		this.flightBooked=flightBooked;
	}
	
	
}
