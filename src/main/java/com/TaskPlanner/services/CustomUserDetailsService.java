package com.TaskPlanner.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TaskPlanner.models.User;
import com.TaskPlanner.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Details not found with this username: " + username));

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getRole());
		authorities.add(sga);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);

	}

}
