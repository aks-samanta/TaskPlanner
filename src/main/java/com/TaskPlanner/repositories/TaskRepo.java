package com.TaskPlanner.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskPlanner.models.Task;
import com.TaskPlanner.models.User;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer>{

	List<Task> findByUser(User user);

	Optional<Task> findByIdAndUser(Integer taskId, User user);

}
