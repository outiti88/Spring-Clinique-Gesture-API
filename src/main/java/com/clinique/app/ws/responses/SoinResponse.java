package com.clinique.app.ws.responses;


public class SoinResponse {

	
	private String soinId;
	private String typeSoin;
	private float prix;
	private UserResponse medecin;
	
	
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
	public UserResponse getMedecin() {
		return medecin;
	}
	public void setMedecin(UserResponse medecin) {
		this.medecin = medecin;
	}
	
	
	
}
