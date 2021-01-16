package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.entities.ScannerEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.ScannerRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.ScannerService;
import com.clinique.app.ws.shared.Utils;

@Service
public class ScannerServiceImpl implements ScannerService{
	
	@Autowired
	ScannerRepository scannerRepository;
	
	@Autowired
	Utils util;

	@Override
	public ScannerDto addScanner(ScannerDto scannerDto) {
		scannerDto.setScannerId(util.generateStringId(32));
		ScannerEntity scannerEntity = mapDtoToEntity(scannerDto);
		ScannerEntity createdScannerEntity = scannerRepository.save(scannerEntity);
		ScannerDto createdScannerDto = mapEntityToDto(createdScannerEntity);
		return createdScannerDto;
	}

	@Override
	public ScannerDto updateScanner(ScannerDto scannerDto, String scannerId) {
		ScannerEntity scannerEntity = scannerRepository.findByScannerId(scannerId);
		if(scannerEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		long id = scannerEntity.getId();
		scannerEntity = mapDtoToEntity(scannerDto);
		scannerEntity.setId(id);
		scannerEntity.setScannerId(scannerId);
		ScannerEntity updatedScannerEntity = scannerRepository.save(scannerEntity);
		ScannerDto updatedScannerDto = mapEntityToDto(updatedScannerEntity);
		return updatedScannerDto;
	}

	@Override
	public List<ScannerDto> getScanners() {
		List<ScannerEntity> scannerEntities = new ArrayList<ScannerEntity>();
		List<ScannerDto> scannerDtos = new ArrayList<ScannerDto>();
		scannerEntities = (List<ScannerEntity>)scannerRepository.findAll();
		scannerEntities.stream().forEach(scannerEntity -> {
			ScannerDto scannerDto = mapEntityToDto(scannerEntity);
			scannerDtos.add(scannerDto);
		});
		return scannerDtos;
	}

	@Override
	public void deleteScanner(String scannerId) {
		ScannerEntity scannerEntity = scannerRepository.findByScannerId(scannerId);
		if(scannerEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		scannerRepository.delete(scannerEntity);
	}
	
	private ScannerDto mapEntityToDto(ScannerEntity scannerEntity) {
		ScannerDto scannerDto = new ScannerDto();
		scannerDto.setName(scannerEntity.getName());
		scannerDto.setPrice(scannerEntity.getPrice());
		scannerDto.setScannerId(scannerEntity.getScannerId());
		return scannerDto;
	}
	
	private ScannerEntity mapDtoToEntity(ScannerDto scannerDto) {
		ScannerEntity scannerEntity = new ScannerEntity();
		scannerEntity.setName(scannerDto.getName());
		scannerEntity.setPrice(scannerDto.getPrice());
		scannerEntity.setScannerId(scannerDto.getScannerId());
		return scannerEntity;
	}

}
