package com.TaskPlanner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskPlanner.dtos.UserDto;
import com.TaskPlanner.exceptions.UserNotFoundException;
import com.TaskPlanner.services.UserServices;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserServices userServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) throws UserNotFoundException {

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		UserDto addedUser = userServices.addUser(userDto);
		return ResponseEntity.ok(addedUser);
	}

	@GetMapping("/signIn")
	public ResponseEntity<UserDto> getLoggedInCustomerDetailsHandler(Authentication auth) {

		UserDto userDto = userServices.getUserByUsername(auth.getName());

		// to get the token in body, pass HttpServletResponse inside this method
		// parameter
		// System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));
		System.out.println("signIn");
		return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);

	}

	@PutMapping("/updateUsername")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<UserDto> updateUsername(
			@RequestParam @Email(message = "Invalid email format") @NotNull(message = "Username cannot be null") String newEmail)
			throws UserNotFoundException {
		UserDto updatedUser = userServices.updateUsername(getCurrentUserId(), newEmail);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<UserDto> deleteUser() throws UserNotFoundException {
		UserDto deletedUser = userServices.deleteUser(getCurrentUserId());
		return ResponseEntity.ok(deletedUser);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<UserDto> getUser() throws UserNotFoundException {
		UserDto user = userServices.getUser(getCurrentUserId());
		return ResponseEntity.ok(user);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) throws UserNotFoundException {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		UserDto updatedUser = userServices.updateUser(userDto);
		return ResponseEntity.ok(updatedUser);
	}

	// Helper method to get the ID of the currently logged-in user from the Spring
	// Security context
	private Integer getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.userServices.getUserIdByEmail(auth.getPrincipal().toString());
	}
}
