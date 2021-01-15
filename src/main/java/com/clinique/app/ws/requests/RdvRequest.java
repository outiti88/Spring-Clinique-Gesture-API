package com.clinique.app.ws.requests;

public class RdvRequest {
	
	private String motif;
	private String date;
	private String startTime;
	private String endTime;
	private String patientId;
	private String medecinId;
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getMedecinId() {
		return medecinId;
	}
	public void setMedecinId(String medecinId) {
		this.medecinId = medecinId;
	}
	
	

}
