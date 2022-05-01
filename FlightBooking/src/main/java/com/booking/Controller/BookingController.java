package com.booking.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.booking.Exception.DataNotFoundException;
import com.booking.Exception.InvalidDateFormatException;
import com.booking.Exception.InvalidPassportException;
import com.booking.Exception.InvalidUserException;
import com.booking.Exception.NoSuchBookingIdException;
import com.booking.Model.FlightBooking;
import com.booking.Service.BookingService;
import com.booking.vo.Flight;
import com.booking.vo.Passenger;
import com.booking.vo.ResponseTemplateVO;
import com.booking.vo.User;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	private static final String FLIGHT_ID_URL="http://localhost:8020/flight/get-flight-by-id/";
	private static final String FLIGHT_UPDATE_URL="http://localhost:8020/flight/update-flight";
	private static final String USER_URL="http://localhost:8020/user/get-user/";
	private static final String PASSENGER_URL="http://localhost:8020/passenger/addPassenger";
	
	private static final String PASSPORT_PATTERN = "[a-zA-Z0-9]{10}";
	private static final String DATE_PATTERN="[0-9]{2}-[0-9]{2}-[0-9]{4}";
	
	
	private static int getTravelCost(String fleetName,String seatType) {
		List<String> flightNames = Arrays.asList(
					"Air India",
					"Spicejet",
					"Indigo",
					"Go Air",
					"Air Asia",
					"Vistara"
				);
		boolean isPresent=false;
		for(String flight: flightNames) {
			if(flight.equalsIgnoreCase(fleetName)) {
				isPresent=true;
				break;
			}
		}
		if(isPresent) {
			if(seatType.equalsIgnoreCase("Economy")) {
				return 9000;
			}
			else if(seatType.equalsIgnoreCase("Business")) {
				return 15000;
			}
			else {
				if(seatType.equalsIgnoreCase("First Class")) {
					return 20000;
				}
			}
		}
		if(seatType.equalsIgnoreCase("Economy")) {
			return 50000;
		}
		else if(seatType.equalsIgnoreCase("Business")) {
			return 75000;
		}
		else {
			return 100000;
		}
	}
	
	private static void validatePassportFormat(String passportNo) {
		if(!passportNo.matches(PASSPORT_PATTERN)) {
			throw new InvalidPassportException("Please Enter the passport in the right format.");
		}
	}
	
	private static void validateDateFormat(String date) {
		if(!date.matches(DATE_PATTERN)) {
			throw new InvalidDateFormatException("Date entered should be in dd-mm-yyyy format.");
		}
	}
	
	private static void setFlightParameters(
			FlightBooking flightBooking,
			double travelCost,
			int flightId,
			int passengerId) {
		flightBooking.setTravelCost(travelCost);
		flightBooking.setTotalCost(flightBooking.getTravelCost()+2000);
		flightBooking.setFlightId(flightId);
		flightBooking.setPassengerId(passengerId);
	}
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	ResponseTemplateVO responseTemplateVO;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/get-all-booking-history/{userId}")
	public ResponseEntity<List<FlightBooking>> getAllBookings(@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return null;
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admin can can view the bookings.");
		}
		if(bookingService.getAllBookingHistory().isEmpty()) {
			throw new DataNotFoundException("Your booking history is empty!");
		}
		return new ResponseEntity<>(bookingService.getAllBookingHistory(),HttpStatus.OK);
	}
	
	
	@GetMapping("/get-booking-by-booking-id/{userId}/{bookingId}")
	public ResponseEntity<Optional<FlightBooking>> getBookingById(
			@PathVariable("bookingId") int bookingId,
			@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return null;
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admin can can view the bookings.");
		}
		if(bookingService.getBookingByBookingId(bookingId).isEmpty() ) {
			throw new NoSuchBookingIdException("There is no booking record with Booking ID: "+bookingId);
		}
		return new ResponseEntity<>(bookingService.getBookingByBookingId(bookingId),HttpStatus.OK);
	}
	
	@GetMapping("/get-booking-flight/{bookingId}/{userId}")
	public ResponseEntity<ResponseTemplateVO> getBookingAndFlight(
			@PathVariable("bookingId") int bookingId,
			@PathVariable("userId") int userId){
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return null;
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admin can can view the bookings.");
		}
		Optional<FlightBooking> booking = bookingService.getBookingByBookingId(bookingId);
		if(booking.isEmpty()) {
			throw new NoSuchBookingIdException("There is no booking record with Booking ID: "+bookingId);
		}
		Flight flight=restTemplate.getForObject(FLIGHT_ID_URL+"/"+userId+"/"+booking.get().getFlightId(), Flight.class);
		ResponseTemplateVO rt = new ResponseTemplateVO(booking.get(),flight);
		return new ResponseEntity<>(rt,HttpStatus.OK);
	}
	
	@PostMapping("/book-a-flight/{userId}/{flightId}")
	public ResponseEntity<String> addBooking(
			@RequestBody FlightBooking flightBooking,
			@PathVariable("userId") int userId,
			@PathVariable("flightId") int flightId,
			@RequestParam("passportNo") String passportNo,
			@RequestParam("mealPref") String mealPref) {
		User user=null;
		Flight flight=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
			 flight = restTemplate.getForObject(FLIGHT_ID_URL+"/"+userId+"/"+flightId, Flight.class);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}
		if(!user.getUserType().equalsIgnoreCase("User")) {
			throw new InvalidUserException("Only registered users can book a flight.");
		}
		validatePassportFormat(passportNo);
		Passenger passenger = new Passenger(
				user.getFirstName(),
				user.getLastName(),
				passportNo,
				mealPref,
				flight.getFleetName()+" : "+flight.getDepartureLoc()+" => "+flight.getArrivalLoc());
		Passenger p = restTemplate.postForObject(PASSENGER_URL,passenger, Passenger.class);
		flight.setRemainingSeats(flight.getRemainingSeats()-1);
		restTemplate.put(FLIGHT_UPDATE_URL, flight);
		validateDateFormat(flightBooking.getBookingDate());
		setFlightParameters(flightBooking, getTravelCost(flight.getFleetName(),flightBooking.getSeatType()), flightId, p.getPassengerId());
		return new ResponseEntity<>(bookingService.addBooking(flightBooking),HttpStatus.OK);
	}

	@DeleteMapping("/cancel-booking/{userId}/{bookingId}") 
	public ResponseEntity<String> deleteBookingByBid(
			@PathVariable("bookingId") int bookingId,
			@PathVariable("userId") int userId) { 
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}
		if(!user.getUserType().equalsIgnoreCase("User")) {
			throw new InvalidUserException("Only registered users can delete the booking.");
		}
		if(!bookingService.getBookingByBookingId(bookingId).isPresent() ) {
			throw new NoSuchBookingIdException("There is no booking record with Booking ID: "+bookingId);
		}
		return new ResponseEntity<>(bookingService.deleteByBookingId(bookingId),HttpStatus.OK); 
	}
	

}
