package com.project.rest.usersRestApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.rest.usersRestApi.modal.User;
import com.project.rest.usersRestApi.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to a Spring Boot REST API";
	}

	@GetMapping
	public List<User> findAll() {
		return repository.findAll();
	}

	@PostMapping
	public User addUser(@RequestBody User user) {
		return repository.save(user);
	}

	@PutMapping
	public User put(@RequestBody User user) {
		if (user.getId() != null && user.getId() > 0)
			return repository.save(user);
		return user;
	}

	@DeleteMapping
	public String deleteUser(@RequestBody User user) {
		if (user.getId() > 0) {
			repository.delete(user);
			return "User deleted";
		}
		
		return "User not found";
	}

}
