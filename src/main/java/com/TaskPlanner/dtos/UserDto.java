package com.TaskPlanner.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;
	
	@Email(message = "Username must be a valid email address")
	@NotBlank(message = "Username must not be blank")
	@NotNull(message = "Username must not be null")
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "Password must not be blank")
	@NotNull(message = "Password must not be null")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
		message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long")
	private String password;
	
	@NotBlank(message = "First name must not be blank")
	@NotNull(message = "First name must not be null")
	private String firstName;
	
	@NotBlank(message = "Last name must not be blank")
	@NotNull(message = "Last name must not be null")
	private String lastName;
	
	@JsonProperty(access = Access.READ_ONLY)
	private final String role = "ROLE_USER";
}

