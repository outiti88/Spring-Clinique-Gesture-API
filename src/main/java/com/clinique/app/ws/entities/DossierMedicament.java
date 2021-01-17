package com.clinique.app.ws.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.clinique.app.ws.shared.DossierMedicamentId;

@Entity(name = "DossierMedicament")
@Table(name = "dossier_medicament")
public class DossierMedicament  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5051004124799263079L;
	
	@EmbeddedId
    private DossierMedicamentId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dossierId")
	private Dossier dossier;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicamentId")
	private Medicament medicament;
	
	@Column(nullable = false)
	private int qty;
	
	private DossierMedicament() {}
	 
    public DossierMedicament(Dossier dossier, Medicament medicament, int qty) {
        this.dossier = dossier;
        this.medicament = medicament;
        this.id = new DossierMedicamentId(dossier.getId(), medicament.getId());
        this.qty = qty;
    }

	public Dossier getDossier() {
		return dossier;
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        DossierMedicament that = (DossierMedicament) o;
        return Objects.equals(dossier, that.dossier) &&
               Objects.equals(medicament, that.medicament);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(dossier, medicament);
    }

}
