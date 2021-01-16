package com.clinique.app.ws.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.requests.ScannerRequest;
import com.clinique.app.ws.responses.ScannerResponse;
import com.clinique.app.ws.services.ScannerService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController 
@RequestMapping("/scanner")
public class ScannerController {
	
	@Autowired
	ScannerService scannerService;
	
	
	@PostMapping
	public ResponseEntity<Object> createScanner(@RequestBody @Valid ScannerRequest scannerRequest){
		ScannerDto scannerDto = new ScannerDto();
		scannerDto.setName(scannerRequest.getName());
		scannerDto.setPrice(scannerRequest.getPrice());
		scannerDto = scannerService.addScanner(scannerDto);
		ScannerResponse scannerResponse = new ScannerResponse();
		BeanUtils.copyProperties(scannerDto, scannerResponse);
		return new ResponseEntity<>(scannerResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ScannerResponse>> getScanners(){
		List<ScannerResponse> scannerResponses = new ArrayList<ScannerResponse>();
		List<ScannerDto> scannerDtos = scannerService.getScanners();
		scannerDtos.stream().forEach(scannerDto -> {
			ScannerResponse scannerResponse = new ScannerResponse();
			BeanUtils.copyProperties(scannerDto, scannerResponse);
			scannerResponses.add(scannerResponse);
		});
		return new ResponseEntity<List<ScannerResponse>>(scannerResponses,HttpStatus.OK);
	}
	
	@PutMapping(path = "/{scannerId}")
	public ResponseEntity<Object> updateScanner(@PathVariable String scannerId, @RequestBody @Valid ScannerRequest scannerRequest){
		ScannerDto scannerDto = new ScannerDto();
		BeanUtils.copyProperties(scannerRequest, scannerDto);
		ScannerDto updatedScanner = scannerService.updateScanner(scannerDto, scannerId);
		ScannerResponse scannerResponse = new ScannerResponse();
		BeanUtils.copyProperties(updatedScanner, scannerResponse);
		return new ResponseEntity<>(scannerResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/{scannerId}")
	public ResponseEntity<Object> deleteScanner(@PathVariable String scannerId){
		scannerService.deleteScanner(scannerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
