package com.clinique.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SoinEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7188342835059400006L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String soinId;
	
	@Column(nullable = false, length = 50)
	private String typeSoin;
	
	@Column(nullable = false, length = 50)
	private float prix;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private UserEntity medecin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSoinId() {
		return soinId;
	}

	public void setSoinId(String soinId) {
		this.soinId = soinId;
	}

	public String getTypeSoin() {
		return typeSoin;
	}

	public void setTypeSoin(String typeSoin) {
		this.typeSoin = typeSoin;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public UserEntity getMedecin() {
		return medecin;
	}

	public void setMedecin(UserEntity medecin) {
		this.medecin = medecin;
	}
	
	
	
}
