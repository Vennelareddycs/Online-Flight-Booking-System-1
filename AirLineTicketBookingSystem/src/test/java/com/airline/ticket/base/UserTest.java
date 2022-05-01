package com.airline.ticket.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.airline.ticket.base.Model.User;
import com.airline.ticket.base.Repository.UserRepo;
import com.airline.ticket.base.Service.UserService;

@SpringBootTest
public class UserTest {
	@MockBean
	private UserRepo repository;
	@Autowired
	private UserService service;

	@Test
	public void addUser() {
		User user = new User(12, "rashi", "rashi124", "rashika", "ka", "user", "user123@gmail.com","female", 18, 766532241,"dfsyjhb fvghjc");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.addUser(user));
	}
	@Test
	public void updateUser() {
		User user = new User(12, "rashi", "rashi124", "rashika", "ka", "user", "user123@gmail.com","female", 18, 766532241,
				"dfsyjhb fvghjc");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.updateUser(user));

	}

}
