package com.clinique.app.ws.dto;

public class DosMedDto {
	
	private int qty;
	private MedicamentDto medicamentDto = new MedicamentDto();
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public MedicamentDto getMedicamentDto() {
		return medicamentDto;
	}
	public void setMedicamentDto(MedicamentDto medicamentDto) {
		this.medicamentDto = medicamentDto;
	}
}
