package com.clinique.app.ws.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DossierDto {

	private String dossierId;
	private List<DosMedDto> dosMedDtos = new ArrayList<DosMedDto>();
	private Set<ScannerDto> scannersDtos = new HashSet<ScannerDto>();
	private Set<SoinDto> soinsDtos = new HashSet<SoinDto>();
	
	public String getDossierId() {
		return dossierId;
	}
	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}
	public List<DosMedDto> getDosMedDtos() {
		return dosMedDtos;
	}
	public void setDosMedDtos(List<DosMedDto> dosMedDtos) {
		this.dosMedDtos = dosMedDtos;
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
