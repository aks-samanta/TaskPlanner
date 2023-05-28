package com.TaskPlanner.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.TaskPlanner.dtos.TaskDto;
import com.TaskPlanner.exceptions.TaskNotFoundException;
import com.TaskPlanner.exceptions.UserNotFoundException;
import com.TaskPlanner.services.TaskServices;
import com.TaskPlanner.services.UserServices;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;

@RestController
@RequestMapping("/api/tasks") // URI prefix for all task-related endpoints
@CrossOrigin(origins = "*")
public class TaskController {

	@Autowired
	private TaskServices taskServices;

	@Autowired
	private UserServices userServices;

	// Endpoint to add a task for the logged-in user
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto task) throws UserNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto addedTask = taskServices.addTask(task, userId);
		return ResponseEntity.ok(addedTask);
	}

	// Endpoint to get all tasks of the logged-in user
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<List<TaskDto>> getAllTasksOfUser() throws UserNotFoundException {
		Integer userId = getCurrentUserId();
		List<TaskDto> tasks = taskServices.getAllTasksOfUser(userId);
		return ResponseEntity.ok(tasks);
	}

	// Endpoint to get a specific task of the logged-in user
	@GetMapping("/{taskId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> getTask(@PathVariable Integer taskId)
			throws UserNotFoundException, TaskNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto task = taskServices.getTask(taskId, userId);
		return ResponseEntity.ok(task);
	}

	// Endpoint to update a specific task of the logged-in user
	@PutMapping("/update/{taskId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto updatedTask, @PathVariable Integer taskId)
			throws UserNotFoundException, TaskNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto updatedTaskDto = taskServices.updateTask(taskId, updatedTask, userId);
		return ResponseEntity.ok(updatedTaskDto);
	}

	// Endpoint to toggle reminder status of a specific task of the logged-in user
	@PutMapping("/{taskId}/reminder")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> toggleReminder(@PathVariable Integer taskId)
			throws UserNotFoundException, TaskNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto updatedTaskDto = taskServices.toggleReminder(taskId, userId);
		return ResponseEntity.ok(updatedTaskDto);
	}

	// Endpoint to toggle done status of a specific task of the logged-in user
	@PutMapping("/{taskId}/done")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> toggleDone(@PathVariable Integer taskId)
			throws UserNotFoundException, TaskNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto updatedTaskDto = taskServices.toggleDone(taskId, userId);
		return ResponseEntity.ok(updatedTaskDto);
	}

	// Endpoint to update deadline of a specific task of the logged-in user
	@PutMapping("/{taskId}/deadline")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> updateDeadline(@PathVariable Integer taskId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") @Future(message = "Deadline must be a future date") Date newDeadline)
			throws UserNotFoundException, TaskNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto updatedTaskDto = taskServices.updateDeadline(taskId, newDeadline, userId);
		return ResponseEntity.ok(updatedTaskDto);
	}

	// Endpoint to delete a specific task of the logged-in user
	@DeleteMapping("/{taskId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<TaskDto> deleteTask(@PathVariable Integer taskId)
			throws UserNotFoundException, TaskNotFoundException {
		Integer userId = getCurrentUserId();
		TaskDto deletedTask = taskServices.deleteTask(taskId, userId);
		return ResponseEntity.ok(deletedTask);
	}

	// Helper method to get the ID of the currently logged-in user from the Spring
	// Security context
	private Integer getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.userServices.getUserIdByEmail(auth.getPrincipal().toString());
	}

}
