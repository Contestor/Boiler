package com.fdmgroup.boiler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.boiler.model.Attribute;
import com.fdmgroup.boiler.model.Method;
import com.fdmgroup.boiler.model.User;
import com.fdmgroup.boiler.repository.MethodRepository;
import com.fdmgroup.boiler.repository.UserRepository;

@PreAuthorize("authenticated")
@WithMockUser("Zane")
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {
	
	private MockMvc mockMvc;
	private Method method;
	private Optional<Method> optionalMethod;
	private User user;
	
	@MockBean
	private MethodRepository mrMock;
	
	@MockBean
	private UserRepository urMock;
	
	@MockBean
	private Method methodMock;
	
	@MockBean
	private User userMock;
	
	@Autowired
	public IntegrationTest (MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	@BeforeEach
	void setUp() {
		user = new User();
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("base", "double"));
		attributes.add(new Attribute("exponent", "double"));
		String code = "public double calculatePower(double base, double exponent) {\n"
				+ "\n"
				+ "\tif (exponent == 0)\n"
				+ "\t\treturn 1;\n"
				+ "\n"
				+ "\tdouble temp = calculatePower(base, exponent / 2);\n"
				+ "\n"
				+ "\tif (exponent % 2 == 0)\n"
				+ "\t\treturn temp * temp;\n"
				+ "\n"
				+ "\telse {\n"
				+ "\t\tif (exponent > 0.0)\n"
				+ "\t\t\treturn base * temp * temp;\n"
				+ "\n"
				+ "\t\telse\n"
				+ "\t\t\treturn (temp * temp) / base;\n"
				+ "\t}\n"
				+ "}";
		method = new Method("Calculate Power", "This method takes two integers and returns the result of the first integer to the power of the second integer.", code, false, attributes, user);
		optionalMethod = Optional.ofNullable(new Method("Calculate Power", "This method takes two integers and returns the result of the first integer to the power of the second integer.", code, false, attributes, user));
	}

	@Test
	void test_get_all_shared_methods() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/methods/search/all")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_get_method_by_name() throws Exception {
		List<Method> methods = new ArrayList<Method>();
		methods.add(method);
		when(mrMock.findBySharedTrueAndNameContains("method")).thenReturn(methods);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/methods/search/method")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_get_method_by_id() throws Exception {
		when(mrMock.findById(1L)).thenReturn(optionalMethod);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/methods").param("id", "1")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_delete_method_by_id() throws Exception {		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/methods").param("id", "1")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_get_user_by_id() throws Exception {
		Optional<User> user = Optional.ofNullable(new User());
		when(urMock.findById(1L)).thenReturn(user);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users").param("id", "1")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_get_user_by_username() throws Exception {
		Optional<User> user = Optional.ofNullable(new User());
		when(urMock.findByUsername("username")).thenReturn(user);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/username")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_get_all_methods_beloning_to_user_by_id() throws Exception {
		List<Method> methods = new ArrayList<Method>();
		methods.add(method);
		when(mrMock.findByUserId(1L)).thenReturn(methods);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/1/methods")).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
	}
	
	@Test
	void test_save_method_by_user_id() throws Exception {
		when(urMock.findById(1L)).thenReturn(Optional.ofNullable(user));
		String jsonMethod = new ObjectMapper().writeValueAsString(method);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/1/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMethod)
			).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

}
