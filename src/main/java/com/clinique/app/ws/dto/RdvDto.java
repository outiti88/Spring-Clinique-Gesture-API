package com.clinique.app.ws.dto;


public class RdvDto {

	private String rdvId;
	private String motif;
	private String date;
	private String startTime;
	private String endTime;
	private PatientDto patient;
	private UserDto medecin;
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
	public PatientDto getPatient() {
		return patient;
	}
	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}
	public UserDto getMedecin() {
		return medecin;
	}
	public void setMedecin(UserDto medecin) {
		this.medecin = medecin;
	}
	
	
	
}
