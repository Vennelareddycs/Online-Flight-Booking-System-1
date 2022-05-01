package com.airline.ticket.base.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.ticket.base.Model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByUserId(int userId);

}