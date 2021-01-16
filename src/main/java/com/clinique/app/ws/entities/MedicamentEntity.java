package com.clinique.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MedicamentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4623603762379103626L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String medicamentId;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String type;
	
	@Column(nullable = false, length = 50)
	private String category;
	
	@Column(nullable = false)
	private float price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(String medicamentId) {
		this.medicamentId = medicamentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
