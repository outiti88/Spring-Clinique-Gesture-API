package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.SoinDto;

public interface SoinService {
	
	public SoinDto addSoin(SoinDto soinDto);
	public SoinDto updateSoin(SoinDto soinDto, String soinId);
	public void deleteSoin(String soinId);
	public List<SoinDto> getSoins();

}
