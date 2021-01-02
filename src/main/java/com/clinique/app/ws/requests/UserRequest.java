package com.clinique.app.ws.requests;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequest {

	@NotNull
	@Size(min = 3)
	private String firstName;
	
	@NotNull
	@Size(min = 3)
	private String lastName;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min = 8)
	private String password;
	
	private List<PatientRequest> patients ;
	
	@NotNull
	private RoleRequest role;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	
	
	public List<PatientRequest> getPatients() {
		return patients;
	}
	public void setPatients(List<PatientRequest> patients) {
		this.patients = patients;
	}
	public RoleRequest getRole() {
		return role;
	}
	public void setRole(RoleRequest role) {
		this.role = role;
	}


	
}
