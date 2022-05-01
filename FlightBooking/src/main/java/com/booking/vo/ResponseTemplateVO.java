package com.booking.vo;

import org.springframework.stereotype.Component;

import com.booking.Model.FlightBooking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponseTemplateVO {

	private FlightBooking flightBooking;
	private Flight flight;
}
