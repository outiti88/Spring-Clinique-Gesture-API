package com.clinique.app.ws.requests;


public class DosMedRequest {

	private String medicamentId;
	private int qty;
	
	
	public String getMedicamentId() {
		return medicamentId;
	}
	public void setMedicamentId(String medicamentId) {
		this.medicamentId = medicamentId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
