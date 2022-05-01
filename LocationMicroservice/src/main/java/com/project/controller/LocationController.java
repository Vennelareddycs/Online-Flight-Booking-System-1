package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.project.exception.IdNotFoundException;
import com.project.exception.InvalidUserException;
import com.project.exception.RecordsNotFoundException;
import com.project.model.Location;
import com.project.service.LocationService;
import com.project.vo.Flight;
import com.project.vo.RestTemplateVO;
import com.project.vo.User;

@Controller
@RequestMapping("/location")
public class LocationController {
	
	private static final String FLIGHTID_URL="http://localhost:8020/flight/get-flight-by-id/";
	private static final String USER_URL="http://localhost:8020/user/get-user/";
	
	@Autowired
	LocationService locserv;
	
	@Autowired
	RestTemplateVO restTemplateVO;
	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping("/addLocation/{userId}")
	public ResponseEntity<String> addLocationDetails(
			@RequestBody Location location,
			@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		try {
			 restTemplate.getForObject(FLIGHTID_URL+"/"+userId+"/"+location.getFlightId(), Flight.class);
		}
		catch(RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}	
		return new ResponseEntity<>(locserv.addLocation(location), HttpStatus.OK);
	}

	@GetMapping("/getAllLoactionDetails/{userId}")
	public ResponseEntity<List<Location>> fetchLocById(@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return null;
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		if(locserv.getLocations().isEmpty()) {
			throw new RecordsNotFoundException("NO RECORDS LEFT TO VIEW");
		}
		return new ResponseEntity<>(locserv.getLocations(), HttpStatus.OK);
	}

	@GetMapping("/getlocById/{userId}/{locationId}")
	public ResponseEntity<Location> fetchLocById(
			@PathVariable("userId") int userId,
			@PathVariable("locationId") int locationId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return null;
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		Location loc = locserv.fetchLocById(locationId);
		if(loc==null) {
			throw new IdNotFoundException("ID NOT FOUND TO FETCH");
		}
		return new ResponseEntity<>(loc, HttpStatus.OK);
	}
	
	@GetMapping("/get-location-and-flight/{userId}/{locationId}")
	public ResponseEntity<RestTemplateVO> getLocationAndFlightDetails(
			@PathVariable("locationId") int locationId,
			@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return null;
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		Location location = locserv.fetchLocById(locationId);
		if(location==null) {
			throw new IdNotFoundException("ID NOT FOUND TO FETCH");
		}
		restTemplateVO.setLocation(location);
		Flight flight = restTemplate.getForObject(FLIGHTID_URL+"/"+userId+"/"+location.getFlightId(), Flight.class);
		restTemplateVO.setFlight(flight);
		return new ResponseEntity<>(restTemplateVO,HttpStatus.OK);
	}
	

	@PutMapping("/updatelocDetails/{userId}")
	public ResponseEntity<String> updateLocById(
			@RequestBody Location location,
			@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		String status = null;
		locserv.updateLocation(location);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@DeleteMapping("/deletelocByid/{userId}/{locationId}")
	public ResponseEntity<String> deleteLocation(
			@PathVariable("locationId") int locationid,
			@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		String status = null;
		Location loc = locserv.fetchLocById(locationid);
		if(loc==null) {
			throw new IdNotFoundException("ID NOT FOUND TO DELETE");
		}
		locserv.deleteLocation(locationid);
		return new ResponseEntity<>("id " + loc + status, HttpStatus.OK);
	}

	@DeleteMapping("/DeleteAll/{userId}")
	public ResponseEntity<String> deleteAll(@PathVariable("userId") int userId) {
		User user=null;
		try {
			 user = restTemplate.getForObject(USER_URL + userId, User.class);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
		}
		if(!user.getUserType().equalsIgnoreCase("Admin")) {
			throw new InvalidUserException("Only admins can view the flight details.");
		}
		if(locserv.getLocations().isEmpty()) {
			throw new RecordsNotFoundException("NO RECORDS LEFT TO DELETE");
		}
		locserv.deleteAllLocations();
		String status = "Successfully deleted all enitities";
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
}
