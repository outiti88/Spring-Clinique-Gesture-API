package com.clinique.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	private String prix;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private UserEntity medecin;
	
}
