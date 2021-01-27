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
import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.dto.RdvDto;
import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.requests.DossierRequest;
import com.clinique.app.ws.responses.DossierResponse;
import com.clinique.app.ws.responses.MedicamentResponse;
import com.clinique.app.ws.responses.PatientResponse;
import com.clinique.app.ws.responses.RdvResponse;
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
		dossierRequest.getMedicamentsIds().stream().forEach(medicamentId -> {
			MedicamentDto medicamentDto = new MedicamentDto();
			medicamentDto.setMedicamentId(medicamentId);
			dossierDto.getMedicamentsDtos().add(medicamentDto);
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
		RdvDto rdvDto = new RdvDto();
		dossierDto.setRdvDto(rdvDto);
		dossierDto.getRdvDto().setRdvId(dossierRequest.getRdvId());
		DossierDto createdDossierDto = dossierService.addDossier(dossierDto);
		DossierResponse dossierResponse = new DossierResponse();
		createdDossierDto.getMedicamentsDtos().stream().forEach(medicamentDto -> {
			MedicamentResponse medicamentResponse = new MedicamentResponse();
			medicamentResponse.setCategory(medicamentDto.getCategory());
			medicamentResponse.setMedicamentId(medicamentDto.getMedicamentId());
			medicamentResponse.setName(medicamentDto.getName());
			medicamentResponse.setPrice(medicamentDto.getPrice());
			medicamentResponse.setType(medicamentDto.getType());
			dossierResponse.getMedicamentsResponses().add(medicamentResponse);
		});
		RdvResponse rdvResponse = new RdvResponse();
		dossierResponse.setRdvResponse(rdvResponse);
		dossierResponse.getRdvResponse().setDate(createdDossierDto.getRdvDto().getDate());
		dossierResponse.getRdvResponse().setEndTime(createdDossierDto.getRdvDto().getEndTime());
		dossierResponse.getRdvResponse().setMotif(createdDossierDto.getRdvDto().getMotif());
		dossierResponse.getRdvResponse().setRdvId(createdDossierDto.getRdvDto().getRdvId());
		dossierResponse.getRdvResponse().setStartTime(createdDossierDto.getRdvDto().getStartTime());
		dossierResponse.getRdvResponse().setState(createdDossierDto.getRdvDto().getState());
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(createdDossierDto.getRdvDto().getMedecin().getEmail());
		userResponse.setFirstName(createdDossierDto.getRdvDto().getMedecin().getFirstName());
		userResponse.setLastName(createdDossierDto.getRdvDto().getMedecin().getLastName());
		userResponse.setUserID(createdDossierDto.getRdvDto().getMedecin().getUserID());
		RoleResponse roleResponse = new RoleResponse();
		roleResponse.setName(createdDossierDto.getRdvDto().getMedecin().getRole().getName());
		roleResponse.setRoleId(createdDossierDto.getRdvDto().getMedecin().getRole().getRoleId());
		userResponse.setRole(roleResponse);
		dossierResponse.getRdvResponse().setMedecin(userResponse);
		PatientResponse patientResponse = new PatientResponse();
		patientResponse.setAdresse(createdDossierDto.getRdvDto().getPatient().getAdresse());
		patientResponse.setCin(createdDossierDto.getRdvDto().getPatient().getCin());
		patientResponse.setNom(createdDossierDto.getRdvDto().getPatient().getNom());
		patientResponse.setPatientId(createdDossierDto.getRdvDto().getPatient().getPatientId());
		patientResponse.setprenom(createdDossierDto.getRdvDto().getPatient().getPrenom());
		patientResponse.setTelephone(createdDossierDto.getRdvDto().getPatient().getTelephone());
		createdDossierDto.getRdvDto().getPatient().getUsers().stream().forEach(userDto -> {
			UserResponse userR = new UserResponse();
			userR.setEmail(userDto.getEmail());
			userR.setFirstName(userDto.getFirstName());
			userR.setLastName(userDto.getLastName());
			userR.setUserID(userDto.getUserID());
			RoleResponse roleR = new RoleResponse();
			roleR.setName(userDto.getRole().getName());
			roleR.setRoleId(userDto.getRole().getRoleId());
			userR.setRole(roleR);
			patientResponse.getUsers().add(userR);
		});
		dossierResponse.getRdvResponse().setPatient(patientResponse);
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
			RoleResponse roleRes = new RoleResponse();
			roleRes.setName(soinDto.getMedecin().getRole().getName());
			roleRes.setRoleId(soinDto.getMedecin().getRole().getRoleId());
			medecinResponse.setRole(roleRes);
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
			dossierDto.getMedicamentsDtos().stream().forEach(medicamentDto -> {
				MedicamentResponse medicamentResponse = new MedicamentResponse();
				medicamentResponse.setCategory(medicamentDto.getCategory());
				medicamentResponse.setMedicamentId(medicamentDto.getMedicamentId());
				medicamentResponse.setName(medicamentDto.getName());
				medicamentResponse.setPrice(medicamentDto.getPrice());
				medicamentResponse.setType(medicamentDto.getType());
				dossierResponse.getMedicamentsResponses().add(medicamentResponse);
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
			dossierResponse.getRdvResponse().setDate(dossierDto.getRdvDto().getDate());
			dossierResponse.getRdvResponse().setEndTime(dossierDto.getRdvDto().getEndTime());
			dossierResponse.getRdvResponse().setMotif(dossierDto.getRdvDto().getMotif());
			dossierResponse.getRdvResponse().setRdvId(dossierDto.getRdvDto().getRdvId());
			dossierResponse.getRdvResponse().setStartTime(dossierDto.getRdvDto().getStartTime());
			dossierResponse.getRdvResponse().setState(dossierDto.getRdvDto().getState());
			UserResponse userResponse = new UserResponse();
			userResponse.setEmail(dossierDto.getRdvDto().getMedecin().getEmail());
			userResponse.setFirstName(dossierDto.getRdvDto().getMedecin().getFirstName());
			userResponse.setLastName(dossierDto.getRdvDto().getMedecin().getLastName());
			userResponse.setUserID(dossierDto.getRdvDto().getMedecin().getUserID());
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setName(dossierDto.getRdvDto().getMedecin().getRole().getName());
			roleResponse.setRoleId(dossierDto.getRdvDto().getMedecin().getRole().getRoleId());
			userResponse.setRole(roleResponse);
			dossierResponse.getRdvResponse().setMedecin(userResponse);
			PatientResponse patientResponse = new PatientResponse();
			patientResponse.setAdresse(dossierDto.getRdvDto().getPatient().getAdresse());
			patientResponse.setCin(dossierDto.getRdvDto().getPatient().getCin());
			patientResponse.setNom(dossierDto.getRdvDto().getPatient().getNom());
			patientResponse.setPatientId(dossierDto.getRdvDto().getPatient().getPatientId());
			patientResponse.setprenom(dossierDto.getRdvDto().getPatient().getPrenom());
			patientResponse.setTelephone(dossierDto.getRdvDto().getPatient().getTelephone());
			dossierDto.getRdvDto().getPatient().getUsers().stream().forEach(userDto -> {
				UserResponse userR = new UserResponse();
				userR.setEmail(userDto.getEmail());
				userR.setFirstName(userDto.getFirstName());
				userR.setLastName(userDto.getLastName());
				userR.setUserID(userDto.getUserID());
				RoleResponse roleR = new RoleResponse();
				roleR.setName(userDto.getRole().getName());
				roleR.setRoleId(userDto.getRole().getRoleId());
				userR.setRole(roleR);
				patientResponse.getUsers().add(userR);
			});
			dossierResponse.getRdvResponse().setPatient(patientResponse);
			dossiersResponses.add(dossierResponse);
		});
		return new ResponseEntity<List<DossierResponse>>(dossiersResponses,HttpStatus.OK);
	}
	
	@PutMapping(path = "/{dossierId}")
	public ResponseEntity<Object> updateDossier(@PathVariable String dossierId, @RequestBody @Valid DossierRequest dossierRequest){
		DossierDto dossierDto = new DossierDto();
		//dossierDto.getDosMedRequest().clear();
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
