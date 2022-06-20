package com.fdmgroup.boiler.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.boiler.model.Method;
import com.fdmgroup.boiler.model.User;
import com.fdmgroup.boiler.service.MethodService;
import com.fdmgroup.boiler.service.UserService;

/**
 * This class is a controller for the users.
 * @author Zane Havemann
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:3000")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserService us;
	private MethodService ms;
	
	@Autowired
	public UserController(UserService us, MethodService ms) {
		this.us = us;
		this.ms = ms;
	}
	
	/**
	 * This is a get request which will return the desired user by id
	 * @param id - This is a long which contains the id of the desired user
	 * @return user - This is a user which contains data on the user
	 */
	@GetMapping
	public User findById(@RequestParam(defaultValue="0") Long id) {
		logger.info("Getting user with id: " + id);
		return this.us.findById(id);
	}
	
	/**
	 * This get request returns the desired user by username
	 * @param username - This is a String which contains the username of the desire user
	 * @return user - This is a user which contains data on the user
	 */
	@GetMapping("{username}")
	public User findByUsername(@PathVariable("username") String username) {
		logger.info("Getting user with username: " + username);
		return this.us.findByUsername(username);
	}
	
	/**
	 * This is a get request which will return all methods owned by the desired user by id
	 * @param id - This is a long which contains the id of the desired user
	 * @return methods - This is a list of methods which contain the methods owned by the desired user
	 */
	@GetMapping("{id}/methods")
	public List<Method> findMethodsByUserId(@PathVariable("id") Long id) {
		logger.info("Getting all methods from user with id: " + id);
		return this.ms.findMethodsByUserId(id);
	}
	
	/**
	 * This is a post request which creates a method within the method repository
	 * @param method - This is a method which contains data about the method
	 */
	@PostMapping("{id}/save")
	public void saveMethod(@RequestBody Method method, @PathVariable("id") Long id) {
		logger.info("Posting method: " + method.toString() + " with user id: " + id);
		this.ms.saveMethod(method, us.findById(id));
	}
	
	/**
	 * This is a put request which updates a method within the method repository
	 * @param method - This is a method which contains data about the method
	 */
	@PutMapping("{id}/update")
	public void updateMethod(@RequestBody Method method, @PathVariable("id") Long id) {
		logger.info("Putting method: " + method.toString() + " with user id: " + id);
		this.ms.saveMethod(method, us.findById(id));
	}

}