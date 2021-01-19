package com.clinique.app.ws.responses;

import java.util.Set;

public class DosMedResponse {
	
	private int qty;
	private MedicamentResponse medicamentResponse;
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public MedicamentResponse getMedicamentResponse() {
		return medicamentResponse;
	}
	public void setMedicamentResponse(MedicamentResponse medicamentResponse) {
		this.medicamentResponse = medicamentResponse;
	}
}
