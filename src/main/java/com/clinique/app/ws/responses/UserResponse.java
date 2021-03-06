package com.clinique.app.ws.responses;

import java.util.List;

public class UserResponse {

	private String userID;
	private String firstName;
	private String lastName;
	private String email;
	private RoleResponse role;
	private List<PatientResponse> patientResponse;
	

	public List<PatientResponse> getPatientResponse() {
		return patientResponse;
	}
	public void setPatientResponse(List<PatientResponse> patientResponse) {
		this.patientResponse = patientResponse;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
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
	
	public RoleResponse getRole() {
		return role;
	}
	public void setRole(RoleResponse role) {
		this.role = role;
	}
	
	
	
	
}
