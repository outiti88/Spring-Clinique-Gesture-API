package com.clinique.app.ws.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.clinique.app.ws.shared.DateDeSerializer;
import com.clinique.app.ws.shared.State;
import com.clinique.app.ws.shared.TimeDeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class RdvEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6639489013142685232L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String rdvId;
	
	@Column(nullable = false, length = 50)
	private String motif;
	
	@Column(nullable = false)
	@JsonDeserialize(using = DateDeSerializer.class)
	private Date date;
	
	@Column(nullable = false)
	@JsonDeserialize(using = TimeDeSerializer.class)
	private Date startTime;
	
	@Column(nullable = false)
	@JsonDeserialize(using = TimeDeSerializer.class)
	private Date endTime;
	
	@ManyToOne
	@JoinColumn(name = "patientId")
	private PatientEntity patient;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private UserEntity medecin;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private State state;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dossier_id", referencedColumnName = "id")
	private Dossier dossier;

	public Dossier getDossier() {
		return dossier;
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRdvId() {
		return rdvId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public UserEntity getMedecin() {
		return medecin;
	}

	public void setMedecin(UserEntity medecin) {
		this.medecin = medecin;
	}
	
	
	
}
