package com.fdmgroup.boiler.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.boiler.model.Method;
import com.fdmgroup.boiler.service.MethodService;

/**
 * This class is a controller for the methods.
 * @author Zane Havemann
 */
@RestController
@RequestMapping("/methods")
@CrossOrigin(origins="http://localhost:3000")
public class MethodController {
	
	Logger logger = LoggerFactory.getLogger(MethodController.class);
	private MethodService ms;
	
	@Autowired
	public MethodController(MethodService ms) {
		this.ms = ms;
	}
	
	/**
	 * This get request will return a list of all shared methods from all users
	 * @return methods - This is a list containing a collection of methods
	 */
	@GetMapping("search/all")
	public List<Method> findAll() {
		logger.info("Getting all shared methods");
		return this.ms.findAll();
	}
	
	/**
	 * This get request will return a list of all share methods which contain the path variable name within its name
	 * @param name - This is a path variable which contains the desired search string
	 * @return methods - This is a list containing a collection of methods
	 */
	@GetMapping("search/{name}")
	public List<Method> findByName(@PathVariable("name") String name) {
		logger.info("Getting all shared methods containing: " + name);
		return this.ms.findByName(name);
	}
	
	/**
	 * This get request will return a method with the id equal to the id parameter within the url
	 * @param id - This is a url parameter which contains the desired method id
	 * @return method - This is a method which contains data about the method
	 */
	@GetMapping
	public Method findById(@RequestParam(defaultValue="0") Long id) {
		logger.info("Getting all methods with id: " + id);
		return this.ms.findById(id);
	}
	
	/**
	 * This is a delete request which deletes a method from the shared methods and the methods repository by id
	 * @param id - This is a long which contains the id of the desire method to delete
	 */
	@DeleteMapping
	public void deleteMethodById(@RequestParam Long id) {
		logger.info("Deleting method with id: " + id);
		this.ms.deleteById(id);
	}
}
