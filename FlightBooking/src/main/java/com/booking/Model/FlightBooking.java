package com.booking.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="flight_booking")
public class FlightBooking {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="booking_id")
	private int bookingId;
	
	@Column(name="passenger_id")
	private int passengerId;
	
	@Column(name = "booking_date")
	private String bookingDate;
	
	@NotNull
	@Column(name="travel_cost")
	private double travelCost;
	
	@NotNull
	@Column(name="total_cost")
	private double totalCost;
	
	@NotEmpty
	@Column(name="seat_type")
	private String seatType;
	
	@Column(name="flight_id")
	private int flightId;
	
	
}
