package com.clinique.app.ws.requests;

import java.util.List;

public class FactureRequest {
	
	private float totalPrice;
	private boolean isPaid;
	private List<String> dossiersIds;
	
	
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
	public List<String> getDossiersIds() {
		return dossiersIds;
	}
	public void setDossiersIds(List<String> dossiersIds) {
		this.dossiersIds = dossiersIds;
	}
	
}
