package com.fdmgroup.boiler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.boiler.model.AuthUserDetails;
import com.fdmgroup.boiler.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	private UserRepository ur;

	@Autowired
	public AuthService(UserRepository ur) {
		super();
		this.ur = ur;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		return new AuthUserDetails(ur.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " does not exist")));
	}
}
