package com.clinique.app.ws.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DossierDto {

	private String dossierId;
	private List<MedicamentDto> medicamentsDtos = new ArrayList<MedicamentDto>();
	private Set<ScannerDto> scannersDtos = new HashSet<ScannerDto>();
	private Set<SoinDto> soinsDtos = new HashSet<SoinDto>();
	private RdvDto rdvDto;
	
	public List<MedicamentDto> getMedicamentsDtos() {
		return medicamentsDtos;
	}
	public void setMedicamentsDtos(List<MedicamentDto> medicamentsDtos) {
		this.medicamentsDtos = medicamentsDtos;
	}
	public RdvDto getRdvDto() {
		return rdvDto;
	}
	public void setRdvDto(RdvDto rdvDto) {
		this.rdvDto = rdvDto;
	}
	public String getDossierId() {
		return dossierId;
	}
	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}
	public Set<ScannerDto> getScannersDtos() {
		return scannersDtos;
	}
	public void setScannersDtos(Set<ScannerDto> scannersDtos) {
		this.scannersDtos = scannersDtos;
	}
	public Set<SoinDto> getSoinsDtos() {
		return soinsDtos;
	}
	public void setSoinsDtos(Set<SoinDto> soinsDtos) {
		this.soinsDtos = soinsDtos;
	}	
}
