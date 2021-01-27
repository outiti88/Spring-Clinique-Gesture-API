package com.clinique.app.ws.dto;

import java.util.ArrayList;
import java.util.List;

public class FactureDto {
	
	private String factureId;
	private float totalPrice;
	private boolean isPaid;
	private List<DossierDto> dossiersDtos = new ArrayList<DossierDto>();
	
	
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
	public List<DossierDto> getDossiersDtos() {
		return dossiersDtos;
	}
	public void setDossiersDtos(List<DossierDto> dossiersDtos) {
		this.dossiersDtos = dossiersDtos;
	}
	
	
	
}
