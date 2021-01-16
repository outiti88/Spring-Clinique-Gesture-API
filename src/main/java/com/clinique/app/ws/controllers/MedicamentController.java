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

import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.requests.MedicamentRequest;
import com.clinique.app.ws.responses.MedicamentResponse;
import com.clinique.app.ws.services.MedicamentService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/medicaments")
public class MedicamentController {
	
	@Autowired
	MedicamentService medicamentService;
	
	@PostMapping
	public ResponseEntity<Object> createMedicament(@RequestBody @Valid MedicamentRequest medicamentRequest){
		MedicamentDto medicamentDto = new MedicamentDto();
		BeanUtils.copyProperties(medicamentRequest, medicamentDto);
		medicamentDto = medicamentService.addMedicament(medicamentDto);
		MedicamentResponse medicamentResponse = new MedicamentResponse();
		BeanUtils.copyProperties(medicamentDto, medicamentResponse);
		return new ResponseEntity<>(medicamentResponse, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<MedicamentResponse>> getMedicaments(){
		List<MedicamentDto> medicamentsDtos = medicamentService.getMedicaments();
		List<MedicamentResponse> medicamentsResponses = new ArrayList<MedicamentResponse>();
		medicamentsDtos.stream().forEach(medicament ->{
			MedicamentResponse medicamentResponse = new MedicamentResponse();
			BeanUtils.copyProperties(medicament, medicamentResponse);
			medicamentsResponses.add(medicamentResponse);
		});
		return new ResponseEntity<List<MedicamentResponse>>(medicamentsResponses, HttpStatus.OK);
	}
	
	@PutMapping(path = "/{medicamentId}")
	public ResponseEntity<Object> updateMedicament(@RequestBody @Valid MedicamentRequest medicamentRequest, @PathVariable String medicamentId){
		MedicamentDto medicamentDto = new MedicamentDto();
		BeanUtils.copyProperties(medicamentRequest, medicamentDto);
		medicamentDto = medicamentService.updateMedicament(medicamentDto, medicamentId);
		MedicamentResponse medicamentResponse = new MedicamentResponse();
		BeanUtils.copyProperties(medicamentDto, medicamentResponse);
		return new ResponseEntity<>(medicamentResponse, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{medicamentId}")
	public ResponseEntity<Object> deleteMedicament(@PathVariable String medicamentId){
		medicamentService.deleteMedicament(medicamentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
