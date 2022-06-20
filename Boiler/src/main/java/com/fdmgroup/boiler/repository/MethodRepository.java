package com.fdmgroup.boiler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.boiler.model.Method;

/**
 * This is a repository which stores all the methods
 * @author Zane Havemann
 */
public interface MethodRepository extends JpaRepository<Method, Long> {
	
	List<Method> findByUserId(Long id);

	List<Method> findBySharedTrue();
	
	List<Method> findBySharedTrueAndNameContains(String name);

}
