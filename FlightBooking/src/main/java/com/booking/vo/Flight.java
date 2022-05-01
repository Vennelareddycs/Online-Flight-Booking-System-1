package com.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
	private int flightId;
	private String arrivalLoc;
	private String departureLoc;
	private String fleetName;
	private String model;
	private Integer remainingSeats;
}
