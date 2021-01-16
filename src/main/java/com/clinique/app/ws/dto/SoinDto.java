package com.clinique.app.ws.dto;


public class SoinDto {

	private String soinId;
	private String typeSoin;
	private float prix;
	private UserDto medecin;
	
	
	public String getSoinId() {
		return soinId;
	}
	public void setSoinId(String soinId) {
		this.soinId = soinId;
	}
	public String getTypeSoin() {
		return typeSoin;
	}
	public void setTypeSoin(String typeSoin) {
		this.typeSoin = typeSoin;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public UserDto getMedecin() {
		return medecin;
	}
	public void setMedecin(UserDto medecin) {
		this.medecin = medecin;
	}
	
	
}
