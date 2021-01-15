package com.clinique.app.ws.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name="patients")
public class PatientEntity implements Serializable {



	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 100 ,nullable=false)
	private String patientId;
	
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
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "patients_users", joinColumns = {@JoinColumn(name="patients_id")}, inverseJoinColumns = {@JoinColumn(name="users_id")})
	private Set<UserEntity> users;
	
	@OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<RdvEntity> rdv;

	
	public Set<RdvEntity> getRdv() {
		return rdv;
	}

	public void setRdv(Set<RdvEntity> rdv) {
		this.rdv = rdv;
	}

	public Set<UserEntity> getUsers() {
		return users;
	}
	
	public void addUser(UserEntity user) {
		this.users.add(user);
		user.getPatients().add(this);
	}
	
	public void removeUser(UserEntity user) {
		this.getUsers().remove(user);
		user.getPatients().remove(this);
	}
	
	public void removeUsers() {
		for (UserEntity user : new HashSet<>(users)) {
            removeUser(user);
        }
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
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
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	

}
