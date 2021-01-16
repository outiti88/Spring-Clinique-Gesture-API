package com.clinique.app.ws.requests;

public class SoinRequest {
	
	private String typeSoin;
	private float prix;
	private String medecinId;
	
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
	public String getMedecinId() {
		return medecinId;
	}
	public void setMedecinId(String medecinId) {
		this.medecinId = medecinId;
	}
	
}
