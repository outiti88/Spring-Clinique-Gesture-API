package com.clinique.app.ws.dto;

import java.util.ArrayList;
import java.util.List;

import com.clinique.app.ws.requests.DosMedRequest;

public class DossierDto {

	private String dossierId;
	private List<DosMedRequest> dosMedRequest = new ArrayList<DosMedRequest>();
	
	public String getDossierId() {
		return dossierId;
	}
	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}
	public List<DosMedRequest> getDosMedRequest() {
		return dosMedRequest;
	}
	public void setDosMedRequest(List<DosMedRequest> dosMedRequest) {
		this.dosMedRequest = dosMedRequest;
	}
	
}
