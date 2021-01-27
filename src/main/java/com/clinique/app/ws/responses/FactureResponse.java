package com.clinique.app.ws.responses;

import java.util.ArrayList;
import java.util.List;

public class FactureResponse {
	
	private String factureId;
	private float totalPrice;
	private boolean isPaid;
	private List<DossierResponse> dossiersResponses = new ArrayList<DossierResponse>();
	
	public String getFactureId() {
		return factureId;
	}
	public void setFactureId(String factureId) {
		this.factureId = factureId;
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
	public List<DossierResponse> getDossiersResponses() {
		return dossiersResponses;
	}
	public void setDossiersResponses(List<DossierResponse> dossiersResponses) {
		this.dossiersResponses = dossiersResponses;
	}
	
}
