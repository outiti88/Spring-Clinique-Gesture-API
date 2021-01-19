package com.clinique.app.ws.entities;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ScannerEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7839378413310577575L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String scannerId;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 50)
	private float price;
	
	@ManyToMany(mappedBy = "scanners", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Dossier> dossiers;

	public Set<Dossier> getDossiers() {
		return dossiers;
	}

	public void setDossiers(Set<Dossier> dossiers) {
		this.dossiers = dossiers;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScannerId() {
		return scannerId;
	}

	public void setScannerId(String scannerId) {
		this.scannerId = scannerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
