package com.clinique.app.ws.services;

import java.util.List;
import com.clinique.app.ws.dto.FactureDto;

public interface FactureService {
	
	public FactureDto addFacture(FactureDto factureDto);
	public List<FactureDto> getFactures();
	public FactureDto updateFacture(FactureDto factureDto);
	public void deleteFacture(String factureId);
	
	
}
