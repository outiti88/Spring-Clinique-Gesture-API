package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.PatientDto;

public interface PatientService {
	
	public PatientDto addPatient(PatientDto patientDto);
	public PatientDto updatePatient(PatientDto patientDto, String patientId);
	public void deletePatient(String patientId);

	List<PatientDto> getAllPatients(String email);

}
