package com.clinique.app.ws.services;

import java.util.List;

import com.clinique.app.ws.dto.ScannerDto;

public interface ScannerService {
	
	public ScannerDto addScanner(ScannerDto scannerDto);
	public ScannerDto updateScanner(ScannerDto scannerDto, String scannerId);
	public List<ScannerDto> getScanners();
	public void deleteScanner(String scannerId);

}
