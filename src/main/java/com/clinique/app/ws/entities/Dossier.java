package com.clinique.app.ws.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Dossier")
@Table(name = "dossier")
public class Dossier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -188112302610794762L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 100 ,nullable=false)
	private String dossierId;
	
	@OneToMany(mappedBy = "dossier", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<DossierMedicament> medicaments = new ArrayList<>();

	public List<DossierMedicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<DossierMedicament> medicaments) {
		this.medicaments = medicaments;
	}
	
	public DossierMedicament addMedicament(Medicament medicament, int qty) {
		DossierMedicament dossierMedicament = new DossierMedicament(this, medicament, qty);
		this.medicaments.add(dossierMedicament);
		medicament.getDossiers().add(dossierMedicament);
		return dossierMedicament;
	}
	
	public void removeMedicament(DossierMedicament medicament) {
		for (Iterator<DossierMedicament> iterator = this.medicaments.iterator();
	             iterator.hasNext(); ) {
	            DossierMedicament dossierMedicament = iterator.next();
	 
	            if (dossierMedicament.getDossier().equals(this) &&
	                    dossierMedicament.getMedicament().equals(medicament)) {
	                iterator.remove();
	                dossierMedicament.getMedicament().getDossiers().remove(dossierMedicament);
	                dossierMedicament.setMedicament(null);
	                dossierMedicament.setDossier(null);
	            }
	        }
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDossierId() {
		return dossierId;
	}

	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}
	
	
	
}
