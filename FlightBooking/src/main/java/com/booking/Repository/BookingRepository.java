package com.booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.Model.FlightBooking;

public interface BookingRepository extends JpaRepository<FlightBooking, Integer> {

	List<FlightBooking> findAllBypassengerId(int passengerId);

	
}
