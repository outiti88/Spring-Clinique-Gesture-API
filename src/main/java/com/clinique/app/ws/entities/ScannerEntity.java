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
	private String price;
	
}
