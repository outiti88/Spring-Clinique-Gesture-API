package com.clinique.app.ws.responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DossierResponse {

	private String dossierId;
	private List<DosMedResponse> dosMedResponses = new ArrayList<DosMedResponse>();
	private Set<ScannerResponse> scannersResponses = new HashSet<ScannerResponse>();
	private Set<SoinResponse> soinsResponses = new HashSet<SoinResponse>();
	
	public String getDossierId() {
		return dossierId;
	}
	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}
	public List<DosMedResponse> getDosMedResponses() {
		return dosMedResponses;
	}
	public void setDosMedResponses(List<DosMedResponse> dosMedResponses) {
		this.dosMedResponses = dosMedResponses;
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
