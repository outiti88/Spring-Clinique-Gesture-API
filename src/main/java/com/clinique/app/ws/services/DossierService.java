package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.DossierDto;

public interface DossierService {

	public DossierDto addDossier(DossierDto dossier);
	public DossierDto updateDossier(DossierDto dossier, String dossierId);
	public List<DossierDto> getDossiers();
	public void deleteDossier(String dossierId);
	
}
