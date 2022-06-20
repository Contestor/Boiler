package com.fdmgroup.boiler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.boiler.exceptions.NotFoundException;
import com.fdmgroup.boiler.model.User;
import com.fdmgroup.boiler.repository.UserRepository;

/**
 * This class services for users
 * @author Zane Havemann
 */
@Service
public class UserService {
	
	private UserRepository ur;
	
	@Autowired
	public UserService(UserRepository ur) {
		this.ur = ur;
	}
	
	/**
	 * This method searches the user repository for desired user by id
	 * @param id - This is a long containing the id of the desired user
	 * @return user - This is a user which contains data on the user
	 */
	public User findById(Long id) {
		return this.ur.findById(id).orElseThrow(()->new NotFoundException("User: " + id + " Cannot be found"));
	}
	
	/**
	 * This method searches the user repository for the desired user by username
	 * @param username - This is a string containing the username of the desired user
	 * @return user - This is a user which contains data on the user
	 */
	public User findByUsername(String username) {
		return this.ur.findByUsername(username).orElse(null);
	}

}
