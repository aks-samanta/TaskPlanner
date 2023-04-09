package com.TaskPlanner.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(unique = true)
	private String username;
	private String password;
	
	private String firstName;
	private String lastName;
	
	private final String role = "ROLE_USER";
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Task> tasks = new ArrayList<Task>();
	
}
