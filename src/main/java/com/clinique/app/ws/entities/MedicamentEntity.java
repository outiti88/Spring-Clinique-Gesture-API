package com.clinique.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	private double price;
	
}
