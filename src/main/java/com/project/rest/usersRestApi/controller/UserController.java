package com.project.rest.usersRestApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.rest.usersRestApi.handler.BusinessException;
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
		validateUser(user);
		if (repository.findByUserName(user.getUserName()).isPresent()) {
			throw new BusinessException("Username already exists. Try with another username");
		}
		return repository.save(user);
	}

	@PutMapping
	public User put(@RequestBody User user) {
		validateUser(user);
		if (user.getId() != null && user.getId() >= 0) {
			User existingUser = repository.findById(user.getId())
					.orElseThrow(() -> new BusinessException("User not found. Try with another id"));
			if (!existingUser.getUserName().equals(user.getUserName())
					&& repository.findByUserName(user.getUserName()).isPresent()) {
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
		if (user.getUserName() == null || user.getUserName().isEmpty()) {
			throw new BusinessException("Username is required");
		}
		 if (user.getPassword() == null || user.getPassword().isEmpty()) {
	            throw new BusinessException("Password is required");
	        }
	}

}
