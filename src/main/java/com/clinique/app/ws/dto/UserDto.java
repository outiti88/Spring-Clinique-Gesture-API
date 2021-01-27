package com.clinique.app.ws.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable{

	private static final long serialVersionUID = -878183180419835961L;
	
	
	private long id;
	private String userID;
	private String firstName;
	private String lastName;
	private String email;
	private String emailVerificationToken;
	private Boolean emailVerificationStatus = false;
	private String password;
	private String encryptedPassword;
	private RoleDto role;
	private List<PatientDto> patientDto;

	public List<PatientDto> getPatientDto() {
		return patientDto;
	}
	public void setPatientDto(List<PatientDto> patientDto) {
		this.patientDto = patientDto;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}
	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}
	public Boolean isEmailVerificationStatus() {
		return emailVerificationStatus;
	}
	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	
	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	
	public RoleDto getRole() {
		return role;
	}
	public void setRole(RoleDto role) {
		this.role = role;
	}
	
	
	
	

}
