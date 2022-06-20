package com.fdmgroup.boiler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.boiler.exceptions.NotFoundException;
import com.fdmgroup.boiler.model.Method;
import com.fdmgroup.boiler.model.User;
import com.fdmgroup.boiler.repository.MethodRepository;

/**
 * This class services for methods
 * @author Zane Havemann
 */
@Service
public class MethodService {
	
	MethodRepository mr;
	
	@Autowired
	public MethodService(MethodRepository mr) {
		this.mr = mr;
	}
	
	/**
	 * This method searches the method repository for all methods where shared equals true
	 * @return methods - This is a list containing a collection of methods
	 */
	public List<Method> findAll() {
		return this.mr.findBySharedTrue();
	}
	
	/**
	 * This method searches the method repository for all methods where name contains a desired string and shared equals true
	 * @param name - This is a String which contains the search text
	 * @return methods - This is a list containing a collection of methods
	 */
	public List<Method> findByName(String name) {
		System.out.println(name);
		return this.mr.findBySharedTrueAndNameContains(name);
	}
	
	/**
	 * This method searches the method repository for the desired method by id
	 * @param id - This is a long which contains the id of the desire method
	 * @return method - This is a method which contains data about the method
	 */
	public Method findById(Long id) {
		return this.mr.findById(id).orElseThrow(()->new NotFoundException("Method: " + id + " Cannot be found"));
	}
	
	/**
	 * This method saves the desired method to the method repository
	 * @param method - This is a method which contains data about the method
	 * @param user - This is a user which contains data on the user
	 */
	public void saveMethod(Method method, User user) {
		method.setUser(user);
		this.mr.save(method);
	}

	/**
	 * This method deletes the desired method from the method repository
	 * @param id - This is a long which contains the id of the desire method
	 */
	public void deleteById(Long id) {
		this.mr.deleteById(id);
	}
	
	/**
	 * This method searches the method repository for all methods with the desired user id
	 * @param id - This is a long which contains the id of the desire user
	 * @return methods - This is a list containing a collection of methods
	 */
	public List<Method> findMethodsByUserId(Long id) {
		return this.mr.findByUserId(id);
	}

}
