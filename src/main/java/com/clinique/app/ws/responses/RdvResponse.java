package com.clinique.app.ws.responses;


public class RdvResponse {

	private String rdvId;
	private String motif;
	private String date;
	private String startTime;
	private String endTime;
	private PatientResponse patient;
	private UserResponse medecin;
	private String state;
	
	public String getRdvId() {
		return rdvId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setRdvId(String rdvId) {
		this.rdvId = rdvId;
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
	public PatientResponse getPatient() {
		return patient;
	}
	public void setPatient(PatientResponse patient) {
		this.patient = patient;
	}
	public UserResponse getMedecin() {
		return medecin;
	}
	public void setMedecin(UserResponse medecin) {
		this.medecin = medecin;
	}
	
	
}
