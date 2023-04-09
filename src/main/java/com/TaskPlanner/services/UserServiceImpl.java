package com.TaskPlanner.services;

import com.TaskPlanner.dtos.UserDto;
import com.TaskPlanner.models.User;
import com.TaskPlanner.exceptions.UserNotFoundException;
import com.TaskPlanner.repositories.UserRepo;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto addUser(UserDto userDto) throws UserNotFoundException {
		
		Optional<User> foundUser = userRepo.findByUsername(userDto.getUsername());
		if(foundUser.isPresent()) {
			throw new UserNotFoundException("User Already exixts with username : " + userDto.getUsername());
		}
		
		User user = modelMapper.map(userDto, User.class);
		User savedUser = userRepo.save(user);
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUsername(Integer userId, String newUsername) throws UserNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Optional<User> userWithNewUsername = userRepo.findByUsername(newUsername);
		if (userWithNewUsername.isPresent()) {
			throw new UserNotFoundException("User Already registered with username : " + newUsername);
		}

		user.setUsername(newUsername);
		User savedUser = userRepo.save(user);
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto deleteUser(Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		userRepo.delete(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUser(Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public Integer getUserIdByEmail(String email) {

		return userRepo.findByUsername(email).get().getId();
	}

	@Override
	public UserDto getUserByUsername(String username) {

		return modelMapper.map(userRepo.findByUsername(username), UserDto.class);
	}
}
