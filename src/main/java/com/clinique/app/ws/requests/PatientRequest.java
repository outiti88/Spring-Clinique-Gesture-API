package com.clinique.app.ws.requests;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PatientRequest {
	
	private String nom;
	private String prenom;
	private String telephone;
	private String adresse;
	private String cin;
	private Set<String> usersIds = new HashSet<>();
	
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
	public Set<String> getUsersIds() {
		return usersIds;
	}
	public void setUsersIds(Set<String> usersIds) {
		this.usersIds = usersIds;
	}

}
