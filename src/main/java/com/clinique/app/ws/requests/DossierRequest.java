package com.clinique.app.ws.requests;

import java.util.Set;

public class DossierRequest {

	private Set<String> medicamentsIds;
	private Set<String> scannersIds;
	private Set<String> soinsIds;
	private String rdvId;

	public Set<String> getMedicamentsIds() {
		return medicamentsIds;
	}

	public void setMedicamentsIds(Set<String> medicamentsIds) {
		this.medicamentsIds = medicamentsIds;
	}

	public String getRdvId() {
		return rdvId;
	}

	public void setRdvId(String rdvId) {
		this.rdvId = rdvId;
	}

	public Set<String> getScannersIds() {
		return scannersIds;
	}

	public void setScannersIds(Set<String> scannersIds) {
		this.scannersIds = scannersIds;
	}

	public Set<String> getSoinsIds() {
		return soinsIds;
	}

	public void setSoinsIds(Set<String> soinsIds) {
		this.soinsIds = soinsIds;
	}
}
