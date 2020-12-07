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
	
	@NotNull
	private String age;
	
	
	@NotNull
	private List<AdresseRequest> adresses ;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	
	public  String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	public List<AdresseRequest> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<AdresseRequest> adresses) {
		this.adresses = adresses;
	}

	
}
