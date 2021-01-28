package com.clinique.app.ws.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Facture")
@Table(name = "facture")
public class Facture implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5464994572626021524L;

	@GeneratedValue
	@Id
	private long id;
	
	@Column(nullable = false)
	private String factureId;
	
	@Column(nullable = false)
	private float totalPrice;
	
	@Column(nullable = false)
	private boolean isPaid;
	
	@OneToMany(mappedBy = "facture", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Dossier> dossiers = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public List<Dossier> getDossiers() {
		return dossiers;
	}

	public void setDossiers(List<Dossier> dossiers) {
		this.dossiers = dossiers;
	}

	public String getFactureId() {
		return factureId;
	}

	public void setFactureId(String factureId) {
		this.factureId = factureId;
	}
	
	

}
