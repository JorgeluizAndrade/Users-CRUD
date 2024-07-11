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

import com.project.rest.usersRestApi.handler.BusinessException;
import com.project.rest.usersRestApi.modal.User;
import com.project.rest.usersRestApi.repository.UserRepository;
import com.project.rest.usersRestApi.service.UserService;

@RestController
@RequestMapping("/api/public")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
    private UserService service;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to a Spring Boot REST API";
	}

	@GetMapping
	public List<User> findAll() {
		return repository.findAll();
	}

	@PostMapping
	public void addUser(@RequestBody User user) {
		validateUser(user);
		if (repository.existsByUsername(user.getUsername()).isPresent()) {
			throw new BusinessException("Username already exists. Try with another username");
		}
		 service.createUser(user);
	}

	@PutMapping
	public User put(@RequestBody User user) {
		validateUser(user);
		if (user.getId() != null && user.getId() >= 0) {
			User existingUser = repository.findById(user.getId())
					.orElseThrow(() -> new BusinessException("User not found. Try with another id"));
			if (!existingUser.getUsername().equals(user.getUsername())
					&& repository.existsByUsername(user.getUsername()).isPresent()) {
				throw new BusinessException("Username already exists. Try with another username");
			}
			return repository.save(user);

		} else {
			throw new BusinessException("User not found. Try with another id");
		}
	}

	@DeleteMapping
	public String deleteUser(@RequestBody User user) {
		if (user.getId() != null && user.getId() >= 0) {
			User existingUser = repository.findById(user.getId())
					.orElseThrow(() -> new BusinessException("User not found. Try with another id"));

			existingUser.getId();
			repository.delete(user);
			return "User deleted";
		} else {
			throw new BusinessException("User not found. Try with another id");
		}
	}

	private void validateUser(User user) {
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new BusinessException("Name is required");
		}
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new BusinessException("Username is required");
		}
		 if (user.getPassword() == null || user.getPassword().isEmpty()) {
	            throw new BusinessException("Password is required");
	        }
	}

}
