package com.TaskPlanner.services;

import com.TaskPlanner.dtos.UserDto;
import com.TaskPlanner.exceptions.UserNotFoundException;

public interface UserServices {

	UserDto addUser(UserDto user) throws UserNotFoundException;

	UserDto updateUsername(Integer userId, String newEmail) throws UserNotFoundException;

	UserDto deleteUser(Integer userId) throws UserNotFoundException;

	UserDto getUser(Integer userId) throws UserNotFoundException;

	Integer getUserIdByEmail(String email);

	UserDto getUserByUsername(String username);
}
