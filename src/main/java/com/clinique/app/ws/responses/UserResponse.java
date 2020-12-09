package com.clinique.app.ws.responses;

import java.util.List;

public class UserResponse {

	private String userID;
	private String firstName;
	private String lastName;
	private String email;
	private List<AdresseResponse> adresses;
	private ContactResponse contact;
	
	
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
	public List<AdresseResponse> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<AdresseResponse> adresses) {
		this.adresses = adresses;
	}
	public ContactResponse getContact() {
		return contact;
	}
	public void setContact(ContactResponse contact) {
		this.contact = contact;
	}

	
	
}
