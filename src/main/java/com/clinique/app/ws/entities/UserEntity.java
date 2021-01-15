package com.clinique.app.ws.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "users")
public class UserEntity implements Serializable {

	
	private static final long serialVersionUID = 2919143842240940287L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String userID;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(nullable = true)
	private String emailVerificationToken;

	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;

	@Column(nullable = false)
	private String encryptedPassword;
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	private Set<PatientEntity> patients;
	
	@ManyToOne
	@JoinColumn(name = "roleId")
	private RoleEntity role;
	
	@OneToMany(mappedBy = "medecin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<RdvEntity> rdvs;
	
	@OneToMany(mappedBy = "medecin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<SoinEntity> soins;
	
	public Set<SoinEntity> getSoins() {
		return soins;
	}

	public void setSoins(Set<SoinEntity> soins) {
		this.soins = soins;
	}

	public Set<RdvEntity> getRdvs() {
		return rdvs;
	}

	public void setRdvs(Set<RdvEntity> rdvs) {
		this.rdvs = rdvs;
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

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public Set<PatientEntity> getPatients() {
		return patients;
	}
	
	public void setPatients(Set<PatientEntity> patients) {
		this.patients = patients;
	}


	

}
