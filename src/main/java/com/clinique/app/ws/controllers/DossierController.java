package com.clinique.app.ws.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.requests.DossierRequest;
import com.clinique.app.ws.responses.DossierResponse;
import com.clinique.app.ws.responses.RdvResponse;
import com.clinique.app.ws.services.DossierService;
import com.clinique.app.ws.services.RdvService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/dossiers")
public class DossierController {

	@Autowired
	DossierService dossierService;
	
	@PostMapping
	public ResponseEntity<Object> createDossier(@RequestBody @Valid DossierRequest dossierRequest){
		DossierDto dossierDto = new DossierDto();
		dossierDto.getDosMedRequest().clear();
		dossierRequest.getDosMedRequests().stream().forEach(dosMedRequest -> {
			dossierDto.getDosMedRequest().add(dosMedRequest);
		});
		DossierDto createdDossierDto = dossierService.addDossier(dossierDto);
		ModelMapper modelMapper = new ModelMapper();
		DossierResponse dossierResponse = modelMapper.map(createdDossierDto, DossierResponse.class);
		return new ResponseEntity<>(dossierResponse, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<DossierResponse>> getDossiers(){
		List<DossierDto> dossiersDtos = dossierService.getDossiers();
		List<DossierResponse> dossiersResponses = new ArrayList<DossierResponse>();
		ModelMapper modelMapper = new ModelMapper();
		dossiersDtos.stream().forEach(dossierDto -> {
			DossierResponse dossierResponse = modelMapper.map(dossierDto, DossierResponse.class);
			dossiersResponses.add(dossierResponse);
		});
		return new ResponseEntity<List<DossierResponse>>(dossiersResponses,HttpStatus.OK);
	}
	
	@PutMapping(path = "/{dossierId}")
	public ResponseEntity<Object> updateDossier(@PathVariable String dossierId, @RequestBody @Valid DossierRequest dossierRequest){
		DossierDto dossierDto = new DossierDto();
		dossierDto.getDosMedRequest().clear();
		dossierRequest.getDosMedRequests().stream().forEach(dosMedRequest -> {
			dossierDto.getDosMedRequest().add(dosMedRequest);
		});
		DossierDto updatedDossierDto = dossierService.updateDossier(dossierDto, dossierId);
		ModelMapper modelMapper = new ModelMapper();
		DossierResponse dossierResponse = modelMapper.map(updatedDossierDto, DossierResponse.class);
		return new ResponseEntity<>(dossierResponse, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{dossierId}")
	public ResponseEntity<Object> deleteDossier(@PathVariable String dossierId){
		dossierService.deleteDossier(dossierId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
