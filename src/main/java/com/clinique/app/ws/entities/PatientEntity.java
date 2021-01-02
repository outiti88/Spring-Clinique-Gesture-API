package com.clinique.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="patients")
public class PatientEntity implements Serializable {



	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 100 ,nullable=false)
	private String patientId ;
	
	@Column(length = 10 ,nullable=false)
	private String nom;
	
	@Column(length = 20 ,nullable=false)
	private String prenom;
	
	@Column(length = 10 ,nullable=false)
	private String telephone;
	
	@Column(length = 100 ,nullable=false)
	private String adresse;
	
	@Column(length = 10 ,nullable=false)
	private String cin;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity user;

	
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
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	

}
