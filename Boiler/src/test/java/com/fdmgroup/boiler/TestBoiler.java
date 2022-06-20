package com.fdmgroup.boiler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.boiler.controller.MethodController;
import com.fdmgroup.boiler.controller.UserController;
import com.fdmgroup.boiler.model.Method;
import com.fdmgroup.boiler.model.User;
import com.fdmgroup.boiler.repository.MethodRepository;
import com.fdmgroup.boiler.repository.UserRepository;
import com.fdmgroup.boiler.service.MethodService;
import com.fdmgroup.boiler.service.UserService;

@ExtendWith(MockitoExtension.class)
class TestBoiler {
	
	MethodController mc;
	UserController uc;
	
	@Mock 
	MethodRepository mrMock;
	
	@Mock 
	UserRepository urMock;
	
	@BeforeEach
	void setUp() {
		mc = new MethodController(new MethodService(mrMock));
		uc = new UserController(new UserService(urMock), new MethodService(mrMock));
	}

	@Test
	void test_get_all_shared_methods() {
		// Arrange
		List<Method> methods = new ArrayList<Method>();
		methods.add(new Method());
		methods.add(new Method());
		when(mrMock.findBySharedTrue()).thenReturn(methods);
		
		// Act
		List<Method> result = mc.findAll();
		
		//Assert
		verify(mrMock, times(1)).findBySharedTrue();
		assertEquals(methods, result);
	}
	
	@Test
	void test_get_all_shared_methods_with_the_letter_a() {
		// Arrange
		List<Method> methods = new ArrayList<Method>();
		methods.add(new Method());
		methods.add(new Method());
		when(mrMock.findBySharedTrueAndNameContains("a")).thenReturn(methods);
		
		// Act
		List<Method> result = mc.findByName("a");
		
		//Assert
		verify(mrMock, times(1)).findBySharedTrueAndNameContains("a");
		assertEquals(methods, result);
	}
	
	@Test
	void test_get_method_by_id() {
		// Arrange
		Optional<Method> method = Optional.ofNullable(new Method());
		when(mrMock.findById(1L)).thenReturn(method);
		
		// Act
		Optional<Method> result = Optional.ofNullable(mc.findById(1L));
		
		//Assert
		verify(mrMock, times(1)).findById(1L);
		assertEquals(method, result);
	}
	
	@Test
	void test_cannot_find_method_by_id() {
		// Arrange
		when(mrMock.findById(1L)).thenReturn(Optional.ofNullable(null));
		String exception = "Method: " + 1 + " Cannot be found";
		
		// Act
		Exception e = assertThrows(Exception.class,
				() -> mc.findById(1L));
		
		//Assert
		verify(mrMock, times(1)).findById(1L);
		assertEquals(exception, e.getMessage());
	}
	
	@Test
	void test_delete_method() {
		// Arrange
		Long id = 1L;
		
		// Act
		mc.deleteMethodById(id);
		
		//Assert
		verify(mrMock, times(1)).deleteById(id);
	}
	
	@Test
	void test_get_user_by_id() {
		// Arrange
		Optional<User> user = Optional.ofNullable(new User());
		when(urMock.findById(1L)).thenReturn(user);
		
		// Act
		Optional<User> result = Optional.ofNullable(uc.findById(1L));
		
		//Assert
		verify(urMock, times(1)).findById(1L);
		assertEquals(user, result);
	}
	
	@Test
	void test_cannot_find_user_by_id() {
		// Arrange
		when(urMock.findById(1L)).thenReturn(Optional.ofNullable(null));
		String exception = "User: " + 1 + " Cannot be found";
		
		// Act
		Exception e = assertThrows(Exception.class,
				() -> uc.findById(1L));
		
		//Assert
		verify(urMock, times(1)).findById(1L);
		assertEquals(exception, e.getMessage());
	}
	
	@Test
	void test_get_user_by_username() {
		// Arrange
		Optional<User> user = Optional.ofNullable(new User());
		String username = "Zane";
		when(urMock.findByUsername(username)).thenReturn(user);
		
		// Act
		Optional<User> result = Optional.ofNullable(uc.findByUsername(username));
		
		// Assert
		verify(urMock, times(1)).findByUsername(username);
		assertEquals(user, result);
	}
	
	@Test
	void test_get_all_methods_by_user_id() {
		// Arrange
		List<Method> methods = new ArrayList<Method>();
		methods.add(new Method());
		methods.add(new Method());
		when(mrMock.findByUserId(1L)).thenReturn(methods);
		
		// Act
		List<Method> result = uc.findMethodsByUserId(1L);
		
		//Assert
		verify(mrMock, times(1)).findByUserId(1L);
		assertEquals(methods, result);
	}
	
	@Test
	void test_save_method() {
		// Arrange
		Method method = new Method();
		Optional<User> user = Optional.ofNullable(new User());
		Long id = 1L;
		when(urMock.findById(id)).thenReturn(user);
		
		// Act
		uc.saveMethod(method, id);
		
		//Assert
		verify(mrMock, times(1)).save(method);
	}
	
	@Test
	void test_update_method() {
		// Arrange
		Method method = new Method();
		Optional<User> user = Optional.ofNullable(new User());
		Long id = 1L;
		when(urMock.findById(id)).thenReturn(user);
		
		// Act
		uc.updateMethod(method, id);
		
		//Assert
		verify(mrMock, times(1)).save(method);
	}

}
