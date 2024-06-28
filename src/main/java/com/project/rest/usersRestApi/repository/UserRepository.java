package com.project.rest.usersRestApi.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rest.usersRestApi.modal.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUserName(String userName);

}
