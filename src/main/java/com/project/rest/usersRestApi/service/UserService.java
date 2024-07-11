package com.project.rest.usersRestApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.rest.usersRestApi.modal.User;
import com.project.rest.usersRestApi.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
    private PasswordEncoder encoder;
	
	public void createUser(User user) {
		String pass = user.getPassword();
		
		user.setPassword(encoder.encode(pass));
		repo.save(user);
	}

}
