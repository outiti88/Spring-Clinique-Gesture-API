package com.clinique.app.ws.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Medicament")
@Table(name = "medicament")
public class Medicament implements Serializable {

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
	
	@ManyToMany(mappedBy = "medicaments", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<Dossier> dossiers = new ArrayList<>();

	public List<Dossier> getDossiers() {
		return dossiers;
	}

	public void setDossiers(List<Dossier> dossiers) {
		this.dossiers = dossiers;
	}

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
