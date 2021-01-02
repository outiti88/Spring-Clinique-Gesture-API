package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.PatientDto;

public interface PatientService {
	
	public PatientDto createPatient(PatientDto patientDto, String medecin_id);

	List<PatientDto> getAllPatients(String email);

}
