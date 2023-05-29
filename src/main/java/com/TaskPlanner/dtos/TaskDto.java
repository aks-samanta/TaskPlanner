package com.TaskPlanner.dtos;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;

	@NotBlank(message = "Title must not be blank")
	@NotNull(message = "Title must not be null")
	private String title;

	@NotNull(message = "CreatedAt must not be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmIST")
	private Date createdAt;

	@NotNull(message = "Deadline must not be null")
	@Future(message = "Deadline must be a future date")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmIST")
	private Date deadline;

	@NotBlank(message = "Description must not be blank")
	@NotNull(message = "Description must not be null")
	private String description;

	private Boolean isComplete;

	@NotNull(message = "hasReminder must not be null")
	private Boolean hasReminder;

}
