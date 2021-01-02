package com.clinique.app.ws.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PatientRequest {
	
	private String nom;
	private String prenom;
	private String telephone;
	private String adresse;
	private String cin;
	
	@Size(min = 29)
	@NotNull
	private String medecin_id;
	
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
	public String getMedecin_id() {
		return medecin_id;
	}
	public void setMedecin_id(String medecin_id) {
		this.medecin_id = medecin_id;
	}
	


}
