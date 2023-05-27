package com.TaskPlanner.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskPlanner.dtos.TaskDto;
import com.TaskPlanner.exceptions.TaskNotFoundException;
import com.TaskPlanner.exceptions.UserNotFoundException;
import com.TaskPlanner.models.Task;
import com.TaskPlanner.models.User;
import com.TaskPlanner.repositories.TaskRepo;
import com.TaskPlanner.repositories.UserRepo;

@Service
public class TaskServiceImpl implements TaskServices {

	@Autowired
	private TaskRepo taskRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TaskDto addTask(TaskDto taskDto, Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = modelMapper.map(taskDto, Task.class);
		task.setCreatedAt(new Date());
		task.setUser(user);

		Task savedTask = taskRepo.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasksOfUser(Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		List<Task> tasks = taskRepo.findByUser(user);
		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = taskRepo.findByIdAndUser(taskId, user).orElseThrow(
				() -> new TaskNotFoundException("Task not found with ID: " + taskId + " for user ID: " + userId));

		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public TaskDto updateTask(Integer taskId, TaskDto updatedTaskDto, Integer userId)
			throws UserNotFoundException, TaskNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = taskRepo.findByIdAndUser(taskId, user).orElseThrow(
				() -> new TaskNotFoundException("Task not found with ID: " + taskId + " for user ID: " + userId));

		modelMapper.map(updatedTaskDto, task);

		Task savedTask = taskRepo.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public TaskDto toggleReminder(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = taskRepo.findByIdAndUser(taskId, user).orElseThrow(
				() -> new TaskNotFoundException("Task not found with ID: " + taskId + " for user ID: " + userId));

		task.setHasReminder(!task.getHasReminder());

		Task savedTask = taskRepo.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public TaskDto toggleDone(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = taskRepo.findByIdAndUser(taskId, user).orElseThrow(
				() -> new TaskNotFoundException("Task not found with ID: " + taskId + " for user ID: " + userId));

		task.setIsComplete(!task.getIsComplete());

		Task savedTask = taskRepo.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public TaskDto updateDeadline(Integer taskId, Date newDeadline, Integer userId)
			throws UserNotFoundException, TaskNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = taskRepo.findByIdAndUser(taskId, user)
				.orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));

		task.setDeadline(newDeadline);
		Task updatedTask = taskRepo.save(task);
		return modelMapper.map(updatedTask, TaskDto.class);
	}

	@Override
	public TaskDto deleteTask(Integer taskId, Integer userId) throws UserNotFoundException, TaskNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Task task = taskRepo.findByIdAndUser(taskId, user)
				.orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));

		taskRepo.delete(task);
		return modelMapper.map(task, TaskDto.class);
	}

}
