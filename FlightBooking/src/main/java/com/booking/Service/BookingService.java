package com.booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.Model.FlightBooking;
import com.booking.Repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	
	public List<FlightBooking> getAllBookingHistory() {
		return bookingRepository.findAll();
	}
	
	public Optional<FlightBooking> getBookingByBookingId(int bookingId) {
		return bookingRepository.findById(bookingId);
	}
	

	public List<FlightBooking> getBookingByPassengerId(int passengerId) { 
		return (List<FlightBooking>) bookingRepository.findAllBypassengerId(passengerId); 
	}
	
	
	public String addBooking(FlightBooking flightBooking) {
		bookingRepository.save(flightBooking);
		return "Booking with ID: "+flightBooking.getBookingId()+" has been added.";	
	}

	public String deleteAllBookingsHistory() {
		bookingRepository.deleteAll();
		return "All the Booking History has been deleted.";
	}
	
	public String deleteByBookingId(int bookingId) {
		bookingRepository.deleteById(bookingId);
		return "Booking with ID: "+bookingId+" has been deleted.";
	}
	
	
}
