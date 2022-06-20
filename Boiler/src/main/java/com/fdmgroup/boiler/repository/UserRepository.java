package com.fdmgroup.boiler.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.boiler.model.User;

/**
 * This is a repository which stores all the users
 * @author Zane Havemann
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
