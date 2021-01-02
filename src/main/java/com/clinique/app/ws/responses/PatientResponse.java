package com.clinique.app.ws.responses;

public class PatientResponse {

	private String patientId;
	private String nom;
	private String prenom;
	private String telephone;
	private String adresse;
	private String cin;
	private UserResponse user;
	
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getprenom() {
		return prenom;
	}
	public void setprenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	
	
	
}
