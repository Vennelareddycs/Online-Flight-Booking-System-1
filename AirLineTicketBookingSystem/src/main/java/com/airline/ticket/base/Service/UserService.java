package com.airline.ticket.base.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.airline.ticket.base.Model.User;
import com.airline.ticket.base.Repository.UserRepo;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;

	public User addUser(User userDet) {
		return userRepo.save(userDet);
	}
	
	public User fetchUserById(int userId){
		return userRepo.findByUserId(userId);
	}
	
	public User updateUser(User user){
		return userRepo.save(user);	
	}

}
