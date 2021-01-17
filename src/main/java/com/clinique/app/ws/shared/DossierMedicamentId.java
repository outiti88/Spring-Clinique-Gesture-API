package com.clinique.app.ws.shared;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class DossierMedicamentId
    implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8717707342888687409L;

	@Column(name = "dossier_id")
    private Long dossierId;
 
    @Column(name = "medicament_id")
    private Long medicamentId;
 
    private DossierMedicamentId() {}
 
    public DossierMedicamentId(
        Long dossierId,
        Long medicamentId) {
        this.dossierId = dossierId;
        this.medicamentId = medicamentId;
    } 
 
    public Long getDossierId() {
		return dossierId;
	}

	public void setDossierId(Long dossierId) {
		this.dossierId = dossierId;
	}

	public Long getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Long medicamentId) {
		this.medicamentId = medicamentId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        DossierMedicamentId that = (DossierMedicamentId) o;
        return Objects.equals(dossierId, that.dossierId) &&
               Objects.equals(medicamentId, that.medicamentId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(dossierId, medicamentId);
    }
}
