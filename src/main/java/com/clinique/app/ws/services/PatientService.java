package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.PatientDto;

public interface PatientService {
	
	public PatientDto createPatient(PatientDto patientDto, String medecin_id);

	public List<PatientDto> getAllPatients(String email);
	
	public void deletePatient(String email ,String patientId);
	
	public PatientDto updatePatient(String patientId , PatientDto patientDto);

}
