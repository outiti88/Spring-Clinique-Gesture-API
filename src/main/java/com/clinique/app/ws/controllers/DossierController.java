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

import com.clinique.app.ws.dto.DosMedDto;
import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.requests.DossierRequest;
import com.clinique.app.ws.responses.DosMedResponse;
import com.clinique.app.ws.responses.DossierResponse;
import com.clinique.app.ws.responses.MedicamentResponse;
import com.clinique.app.ws.responses.RoleResponse;
import com.clinique.app.ws.responses.ScannerResponse;
import com.clinique.app.ws.responses.SoinResponse;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.services.DossierService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/dossiers")
public class DossierController {

	@Autowired
	DossierService dossierService;
	
	@PostMapping
	public ResponseEntity<Object> createDossier(@RequestBody @Valid DossierRequest dossierRequest){
		DossierDto dossierDto = new DossierDto();
		dossierDto.getDosMedDtos().clear();
		dossierRequest.getDosMedRequests().stream().forEach(dosMedRequest -> {
			DosMedDto dosMedDto = new DosMedDto();
			dosMedDto.setQty(dosMedRequest.getQty());
			MedicamentDto medicamentDto = new MedicamentDto();
			medicamentDto.setMedicamentId(dosMedRequest.getMedicamentId());
			dosMedDto.setMedicamentDto(medicamentDto);
			dossierDto.getDosMedDtos().add(dosMedDto);
		});
		dossierRequest.getScannersIds().stream().forEach(scannerId -> {
			ScannerDto scannerDto = new ScannerDto();
			scannerDto.setScannerId(scannerId);
			dossierDto.getScannersDtos().add(scannerDto);
		});
		dossierRequest.getSoinsIds().stream().forEach(soinId -> {
			SoinDto soinDto = new SoinDto();
			soinDto.setSoinId(soinId);
			dossierDto.getSoinsDtos().add(soinDto);
		});
		DossierDto createdDossierDto = dossierService.addDossier(dossierDto);
		DossierResponse dossierResponse = new DossierResponse();
		createdDossierDto.getDosMedDtos().stream().forEach(dosMedDto -> {
			DosMedResponse dosMedResponse = new DosMedResponse();
			dosMedResponse.setQty(dosMedDto.getQty());
			MedicamentResponse medicamentResponse = new MedicamentResponse();
			medicamentResponse.setCategory(dosMedDto.getMedicamentDto().getCategory());
			medicamentResponse.setMedicamentId(dosMedDto.getMedicamentDto().getMedicamentId());
			medicamentResponse.setName(dosMedDto.getMedicamentDto().getName());
			medicamentResponse.setPrice(dosMedDto.getMedicamentDto().getPrice());
			medicamentResponse.setType(dosMedDto.getMedicamentDto().getType());
			dosMedResponse.setMedicamentResponse(medicamentResponse);
			dossierResponse.getDosMedResponses().add(dosMedResponse);
		});
		dossierResponse.setDossierId(createdDossierDto.getDossierId());
		createdDossierDto.getScannersDtos().stream().forEach(scannerDto -> {
			ScannerResponse scannerResponse = new ScannerResponse();
			scannerResponse.setName(scannerDto.getName());
			scannerResponse.setPrice(scannerDto.getPrice());
			scannerResponse.setScannerId(scannerDto.getScannerId());
			dossierResponse.getScannersResponses().add(scannerResponse);
		});
		createdDossierDto.getSoinsDtos().stream().forEach(soinDto -> {
			SoinResponse soinResponse = new SoinResponse();
			soinResponse.setSoinId(soinDto.getSoinId());
			soinResponse.setPrix(soinDto.getPrix());
			soinResponse.setTypeSoin(soinDto.getTypeSoin());
			UserResponse medecinResponse = new UserResponse();
			medecinResponse.setEmail(soinDto.getMedecin().getEmail());
			medecinResponse.setFirstName(soinDto.getMedecin().getFirstName());
			medecinResponse.setLastName(soinDto.getMedecin().getLastName());
			medecinResponse.setUserID(soinDto.getMedecin().getUserID());
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setName(soinDto.getMedecin().getRole().getName());
			roleResponse.setRoleId(soinDto.getMedecin().getRole().getRoleId());
			medecinResponse.setRole(roleResponse);
			soinResponse.setMedecin(medecinResponse);
			dossierResponse.getSoinsResponses().add(soinResponse);
		});
		return new ResponseEntity<>(dossierResponse, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<DossierResponse>> getDossiers(){
		List<DossierDto> dossiersDtos = dossierService.getDossiers();
		List<DossierResponse> dossiersResponses = new ArrayList<DossierResponse>();
		dossiersDtos.stream().forEach(dossierDto -> {
			DossierResponse dossierResponse = new DossierResponse();
			dossierDto.getDosMedDtos().stream().forEach(dosMedDto -> {
				DosMedResponse dosMedResponse = new DosMedResponse();
				dosMedResponse.setQty(dosMedDto.getQty());
				MedicamentResponse medicamentResponse = new MedicamentResponse();
				medicamentResponse.setCategory(dosMedDto.getMedicamentDto().getCategory());
				medicamentResponse.setMedicamentId(dosMedDto.getMedicamentDto().getMedicamentId());
				medicamentResponse.setName(dosMedDto.getMedicamentDto().getName());
				medicamentResponse.setPrice(dosMedDto.getMedicamentDto().getPrice());
				medicamentResponse.setType(dosMedDto.getMedicamentDto().getType());
				dosMedResponse.setMedicamentResponse(medicamentResponse);
				dossierResponse.getDosMedResponses().add(dosMedResponse);
			});
			dossierResponse.setDossierId(dossierDto.getDossierId());
			dossierDto.getScannersDtos().stream().forEach(scannerDto -> {
				ScannerResponse scannerResponse = new ScannerResponse();
				scannerResponse.setName(scannerDto.getName());
				scannerResponse.setPrice(scannerDto.getPrice());
				scannerResponse.setScannerId(scannerDto.getScannerId());
				dossierResponse.getScannersResponses().add(scannerResponse);
			});
			dossierDto.getSoinsDtos().stream().forEach(soinDto -> {
				SoinResponse soinResponse = new SoinResponse();
				soinResponse.setSoinId(soinDto.getSoinId());
				soinResponse.setPrix(soinDto.getPrix());
				soinResponse.setTypeSoin(soinDto.getTypeSoin());
				UserResponse medecinResponse = new UserResponse();
				medecinResponse.setEmail(soinDto.getMedecin().getEmail());
				medecinResponse.setFirstName(soinDto.getMedecin().getFirstName());
				medecinResponse.setLastName(soinDto.getMedecin().getLastName());
				medecinResponse.setUserID(soinDto.getMedecin().getUserID());
				RoleResponse roleResponse = new RoleResponse();
				roleResponse.setName(soinDto.getMedecin().getRole().getName());
				roleResponse.setRoleId(soinDto.getMedecin().getRole().getRoleId());
				medecinResponse.setRole(roleResponse);
				soinResponse.setMedecin(medecinResponse);
				dossierResponse.getSoinsResponses().add(soinResponse);
			});
			dossiersResponses.add(dossierResponse);
		});
		return new ResponseEntity<List<DossierResponse>>(dossiersResponses,HttpStatus.OK);
	}
	
	@PutMapping(path = "/{dossierId}")
	public ResponseEntity<Object> updateDossier(@PathVariable String dossierId, @RequestBody @Valid DossierRequest dossierRequest){
		DossierDto dossierDto = new DossierDto();
		//dossierDto.getDosMedRequest().clear();
		dossierRequest.getDosMedRequests().stream().forEach(dosMedRequest -> {
			//dossierDto.getDosMedRequest().add(dosMedRequest);
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
