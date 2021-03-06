package com.airline.ticket.base.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Users")
public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int userId;

		@NotBlank
		@Size(min = 4, message = "Please enter minimum 4 letters")
		private String userName;

		@NotEmpty(message = "Please enter the firstname")
		private String firstName;

		@NotEmpty(message = "Please enter the lastname")
		private String lastName;
		
		@NotEmpty(message = "Please enter the user type")
		private String userType;
		
		private String email;
		
		@Size(min = 2, message = "Please enter minimum 4 letters")
		private String password;
		
		@NotEmpty(message="Please enter the age")
		private String gender;
		
		@NotNull(message = "please enter the age")
		private int age;
		
		@NotNull
		private long mobileNo;
		
		@NotEmpty(message = "Please enter the address")
		@Size(min = 10, max = 40)
		private String address;
}
