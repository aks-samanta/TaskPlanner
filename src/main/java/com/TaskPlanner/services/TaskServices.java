package com.TaskPlanner.services;

import java.util.Date;
import java.util.List;

import com.TaskPlanner.dtos.TaskDto;
import com.TaskPlanner.exceptions.TaskNotFoundException;
import com.TaskPlanner.exceptions.UserNotFoundException;

public interface TaskServices {

	TaskDto addTask(TaskDto task, Integer userId)throws UserNotFoundException;

	List<TaskDto> getAllTasksOfUser(Integer userId) throws UserNotFoundException;

	TaskDto getTask(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException;

	TaskDto updateTask(Integer taskId, TaskDto updatedTask, Integer userId) throws UserNotFoundException, TaskNotFoundException ;

	TaskDto toggleReminder(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException ;

	TaskDto toggleDone(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException ;

	TaskDto updateDeadline(Integer taskId, Date newDeadline, Integer userId) throws UserNotFoundException, TaskNotFoundException ;

	TaskDto deleteTask(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException ;

}
