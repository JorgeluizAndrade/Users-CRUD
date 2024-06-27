package com.project.rest.usersRestApi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rest.usersRestApi.modal.User;

public interface UserRepository extends JpaRepository<User, Integer>{
}
