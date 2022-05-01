
package com.capg.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="passenger")
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int passengerId;
	@NotBlank
	private String firstName;
	@NotBlank
	//@Size(min = 5, message = "lastname must have atleast five character")
	private String lastName;
	
	@NotBlank
	private String passportNo;
	
	@NotBlank
	private String mealPref;
	
	private String flightBooked;
}
