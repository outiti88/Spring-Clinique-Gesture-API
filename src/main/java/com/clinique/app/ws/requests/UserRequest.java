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
	private List<AdresseRequest> adresses ;
	
	@NotNull
	private ContactRequest contact;
	
	
	
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
	

	
	public List<AdresseRequest> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<AdresseRequest> adresses) {
		this.adresses = adresses;
	}
	public ContactRequest getContact() {
		return contact;
	}
	public void setContact(ContactRequest contact) {
		this.contact = contact;
	}

	
}
