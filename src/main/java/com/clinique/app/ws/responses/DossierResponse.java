package com.clinique.app.ws.responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DossierResponse {

	private String dossierId;
	private List<MedicamentResponse> medicamentsResponses = new ArrayList<MedicamentResponse>();
	private Set<ScannerResponse> scannersResponses = new HashSet<ScannerResponse>();
	private Set<SoinResponse> soinsResponses = new HashSet<SoinResponse>();
	private RdvResponse rdvResponse;
	
	public List<MedicamentResponse> getMedicamentsResponses() {
		return medicamentsResponses;
	}
	public void setMedicamentsResponses(List<MedicamentResponse> medicamentsResponses) {
		this.medicamentsResponses = medicamentsResponses;
	}
	public RdvResponse getRdvResponse() {
		return rdvResponse;
	}
	public void setRdvResponse(RdvResponse rdvResponse) {
		this.rdvResponse = rdvResponse;
	}
	public String getDossierId() {
		return dossierId;
	}
	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}
	public Set<ScannerResponse> getScannersResponses() {
		return scannersResponses;
	}
	public void setScannersResponses(Set<ScannerResponse> scannersResponses) {
		this.scannersResponses = scannersResponses;
	}
	public Set<SoinResponse> getSoinsResponses() {
		return soinsResponses;
	}
	public void setSoinsResponses(Set<SoinResponse> soinsResponses) {
		this.soinsResponses = soinsResponses;
	}
	
}
