package com.clinique.app.ws.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
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
import com.clinique.app.ws.dto.FactureDto;
import com.clinique.app.ws.requests.FactureRequest;
import com.clinique.app.ws.responses.DossierResponse;
import com.clinique.app.ws.responses.FactureResponse;
import com.clinique.app.ws.responses.MedicamentResponse;
import com.clinique.app.ws.responses.PatientResponse;
import com.clinique.app.ws.responses.RdvResponse;
import com.clinique.app.ws.responses.RoleResponse;
import com.clinique.app.ws.responses.ScannerResponse;
import com.clinique.app.ws.responses.SoinResponse;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.services.FactureService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/factures")
public class FactureController {
	
	@Autowired
	FactureService factureService;
	
	@PostMapping
	public ResponseEntity<Object> createFacture(@RequestBody @Valid FactureRequest factureRequest){
		FactureDto factureDto = new FactureDto();
		Iterator<String> iterator = factureRequest.getDossiersIds().iterator();
		while (iterator.hasNext()) {
			String dossierId = iterator.next();
			DossierDto dossierDto = new DossierDto();
			dossierDto.setDossierId(dossierId);
			factureDto.getDossiersDtos().add(dossierDto);
		}
		factureDto.setPaid(factureRequest.getIsPaid());
		factureDto.setTotalPrice(factureRequest.getTotalPrice());
		factureDto = factureService.addFacture(factureDto);
		FactureResponse factureResponse = new FactureResponse();
		factureResponse.setFactureId(factureDto.getFactureId());
		factureResponse.setPaid(factureDto.isPaid());
		factureResponse.setTotalPrice(factureDto.getTotalPrice());
		Iterator<DossierDto> iteratorResponse = factureDto.getDossiersDtos().iterator();
		while (iteratorResponse.hasNext()) {
			DossierDto createdDossierDto = iteratorResponse.next();
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
			factureResponse.getDossiersResponses().add(dossierResponse);
		}
		return new ResponseEntity<>(factureResponse, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<FactureResponse>> getFactures(){
		List<FactureDto> facturesDtos = factureService.getFactures();
		List<FactureResponse> facturesResponses = new ArrayList<FactureResponse>();
		Iterator<FactureDto> iterator = facturesDtos.iterator();
		while (iterator.hasNext()) {
			FactureDto factureDto = iterator.next();
			FactureResponse factureResponse = new FactureResponse();
			factureResponse.setFactureId(factureDto.getFactureId());
			factureResponse.setPaid(factureDto.isPaid());
			factureResponse.setTotalPrice(factureDto.getTotalPrice());
			Iterator<DossierDto> dossierIterator = factureDto.getDossiersDtos().iterator();
			while (dossierIterator.hasNext()) {
				DossierDto createdDossierDto = dossierIterator.next();
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
				if (createdDossierDto.getRdvDto() != null) {
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
				}
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
				factureResponse.getDossiersResponses().add(dossierResponse);
			}
			facturesResponses.add(factureResponse);
		}
		return new ResponseEntity<List<FactureResponse>>(facturesResponses,HttpStatus.OK);
	}
	
	@PutMapping(path = "/{factureId}")
	public ResponseEntity<Object> updateFacture(@RequestBody @Valid FactureRequest factureRequest, @PathVariable String factureId){
		FactureDto factureDto = new FactureDto();
		factureDto.setFactureId(factureId);
		factureDto.setPaid(factureRequest.getIsPaid());
		factureDto.setTotalPrice(factureRequest.getTotalPrice());
		Iterator<String> iterator = factureRequest.getDossiersIds().iterator();
		while (iterator.hasNext()) {
			String dossierId = iterator.next();
			DossierDto dossierDto = new DossierDto();
			dossierDto.setDossierId(dossierId);
			factureDto.getDossiersDtos().add(dossierDto);
		}
		factureDto = factureService.updateFacture(factureDto);
		FactureResponse factureResponse = new FactureResponse();
		factureResponse.setFactureId(factureDto.getFactureId());
		factureResponse.setPaid(factureDto.isPaid());
		factureResponse.setTotalPrice(factureDto.getTotalPrice());
		Iterator<DossierDto> iteratorResponse = factureDto.getDossiersDtos().iterator();
		while (iteratorResponse.hasNext()) {
			DossierDto createdDossierDto = iteratorResponse.next();
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
			if (createdDossierDto.getRdvDto()!=null) {
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
			}
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
			factureResponse.getDossiersResponses().add(dossierResponse);
		}
		return new ResponseEntity<>(factureResponse, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{factureId}")
	public ResponseEntity<Object> deleteFacture(@PathVariable String factureId){
		factureService.deleteFacture(factureId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
