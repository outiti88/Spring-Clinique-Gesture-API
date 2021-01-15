package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.RdvDto;

public interface RdvService {
	
	public RdvDto createRdv(RdvDto rdvDto);
	public RdvDto updateRdv(RdvDto rdvDto, String rdvId);
	public void deleteRdv(String rdvId);
	public List<RdvDto> getRdvs();

}
