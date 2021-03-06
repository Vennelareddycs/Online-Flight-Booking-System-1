package com.airline.ticket.base.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airline.ticket.base.Exception.EmailNotFoundException;
import com.airline.ticket.base.Exception.IdNotFoundException;
import com.airline.ticket.base.Exception.InvalidCredentialsException;
import com.airline.ticket.base.Exception.InvalidEmailFormatException;
import com.airline.ticket.base.Exception.InvalidUserException;
import com.airline.ticket.base.Model.User;
import com.airline.ticket.base.Service.UserService;

@RestController
@RequestMapping("/User")
public class UserController {

	private static final String emailPattern = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> addUserDetails(@Validated @RequestBody User user) {
		if (!user.getEmail().matches(emailPattern)) {
			throw new InvalidEmailFormatException("Invalid email format entered.");
		}
		userService.addUser(user);
		return new ResponseEntity<String>("Registration Successful", HttpStatus.OK);
	}

	@PostMapping("/register-guest-user")
	public ResponseEntity<String> addGuestUserDetails(@Validated @RequestBody User user) {
		user.setEmail("NA");
		user.setPassword("NA");
		userService.addUser(user);
		return new ResponseEntity<String>("Guest User Registration Successful", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(
			@Validated @RequestParam("userId") int userId,
			@Validated @RequestParam("email") String email, 
			@Validated @RequestParam("password") String password){
		User user = userService.fetchUserById(userId);
		if(user==null) {
			throw new IdNotFoundException("There is no user with ID "+userId+", please try again.");
		}
		if (user.getUserType().equalsIgnoreCase("Guest User")) {
			throw new InvalidUserException("Guest users must register to login.");
		}
		if (!user.getEmail().equals(email) || !user.getPassword().equals(password)) {
			throw new InvalidCredentialsException("Incorrect credentials entered, please enter a valid one.");
		}
		if (user.getEmail().isEmpty()) {
			throw new EmailNotFoundException("Email cannot be empty.");
		}
		return new ResponseEntity<>("Logged in successfully!", HttpStatus.OK);
	}
	
	@GetMapping("fetchUserDetails")
		public ResponseEntity<User> fetchUserById(@RequestParam("userId") int userId) {
			User user = userService.fetchUserById(userId);
			if(user==null){
				throw new IdNotFoundException("There is no user with ID "+userId+", please try again.");
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
		
		@PutMapping("updateUserDetails")
		public ResponseEntity<String> updateUserById(@RequestBody User user){
			userService.updateUser(user);	
			return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
		}
}
