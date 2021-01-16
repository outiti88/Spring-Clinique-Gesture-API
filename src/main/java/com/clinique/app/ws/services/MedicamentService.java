package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.MedicamentDto;

public interface MedicamentService {

	public MedicamentDto addMedicament(MedicamentDto medicamentDto);
	public MedicamentDto updateMedicament(MedicamentDto medicamentDto, String medicamentId);
	public List<MedicamentDto> getMedicaments();
	public void deleteMedicament(String medicamentId);
	
}
