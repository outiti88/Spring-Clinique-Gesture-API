package com.clinique.app.ws.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
