package com.clinique.app.ws.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.RdvDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.requests.RdvRequest;
import com.clinique.app.ws.responses.PatientResponse;
import com.clinique.app.ws.responses.RdvResponse;
import com.clinique.app.ws.responses.RoleResponse;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.services.RdvService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController 
@RequestMapping("/rdv")
public class RdvController {
	
	@Autowired
	RdvService rdvService;
	
	@PostMapping
	public ResponseEntity<Object> createRdv(@RequestBody @Valid RdvRequest rdvRequest) throws Exception {
		RdvDto rdvDto = new RdvDto();
		BeanUtils.copyProperties(rdvRequest, rdvDto);
		PatientDto patientDto = new PatientDto();
		rdvDto.setPatient(patientDto);
		rdvDto.getPatient().setPatientId(rdvRequest.getPatientId());
		UserDto userDto = new UserDto();
		rdvDto.setMedecin(userDto);
		rdvDto.getMedecin().setUserID(rdvRequest.getMedecinId());
		RdvDto createRdv = rdvService.createRdv(rdvDto);
		ModelMapper modelMapper = new ModelMapper();
		RdvResponse rdvResponse = modelMapper.map(createRdv, RdvResponse.class);
		return new ResponseEntity<>(rdvResponse, HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/medecin/{medecinId}")
	public ResponseEntity<List<RdvResponse>> getRdvsByMedecin(@PathVariable String medecinId){
		List<RdvDto> rdvsDtos = rdvService.getRdvsByMedecin(medecinId);
		List<RdvResponse> rdvsResponses = new ArrayList<RdvResponse>();
		Iterator<RdvDto> iterator = rdvsDtos.iterator();
		while (iterator.hasNext()) {
			RdvDto rdvDto = iterator.next();
			RdvResponse rdvResponse = new RdvResponse();
			rdvResponse.setDate(rdvDto.getDate());
			rdvResponse.setEndTime(rdvDto.getEndTime());
			rdvResponse.setMotif(rdvDto.getMotif());
			rdvResponse.setRdvId(rdvDto.getRdvId());
			rdvResponse.setStartTime(rdvDto.getStartTime());
			rdvResponse.setState(rdvDto.getState());
			UserResponse medecinResponse = new UserResponse();
			medecinResponse.setEmail(rdvDto.getMedecin().getEmail());
			medecinResponse.setFirstName(rdvDto.getMedecin().getFirstName());
			medecinResponse.setLastName(rdvDto.getMedecin().getLastName());
			medecinResponse.setUserID(rdvDto.getMedecin().getUserID());
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setName(rdvDto.getMedecin().getRole().getName());
			roleResponse.setRoleId(rdvDto.getMedecin().getRole().getRoleId());
			medecinResponse.setRole(roleResponse);
			rdvResponse.setMedecin(medecinResponse);
			PatientResponse patientResponse = new PatientResponse();
			patientResponse.setAdresse(rdvDto.getPatient().getAdresse());
			patientResponse.setCin(rdvDto.getPatient().getCin());
			patientResponse.setNom(rdvDto.getPatient().getNom());
			patientResponse.setPatientId(rdvDto.getPatient().getPatientId());
			patientResponse.setprenom(rdvDto.getPatient().getPrenom());
			patientResponse.setTelephone(rdvDto.getPatient().getTelephone());
			rdvDto.getPatient().getUsers().stream().forEach(userDto -> {
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
			rdvResponse.setPatient(patientResponse);
			rdvsResponses.add(rdvResponse);
		}
		return new ResponseEntity<List<RdvResponse>>(rdvsResponses,HttpStatus.OK);
	}
	
	@GetMapping(path = "/filter")
	public ResponseEntity<List<RdvResponse>> getRdvByFilter(
			@RequestParam(value="date", defaultValue = "") String date,
			@RequestParam(value="startTime", defaultValue = "") String startTime,
			@RequestParam(value="endTime", defaultValue = "") String endTime,
			@RequestParam(value="motif", defaultValue = "") String motif,
			@RequestParam(value="state", defaultValue = "") String state
			){
		char c = '%';
		date = c +date + c;
		startTime = c + startTime + c;
		endTime = c + endTime + c;
		motif = c + motif + c;
		state = c + state + c;
		List<RdvDto> rdvsDtos = rdvService.filterRdv(date, startTime, endTime, motif, state);
		List<RdvResponse> rdvsResponses = new ArrayList<RdvResponse>();
		Iterator<RdvDto> iterator = rdvsDtos.iterator();
		while (iterator.hasNext()) {
			RdvDto rdvDto = iterator.next();
			RdvResponse rdvResponse = new RdvResponse();
			rdvResponse.setDate(rdvDto.getDate());
			rdvResponse.setEndTime(rdvDto.getEndTime());
			rdvResponse.setMotif(rdvDto.getMotif());
			rdvResponse.setRdvId(rdvDto.getRdvId());
			rdvResponse.setStartTime(rdvDto.getStartTime());
			rdvResponse.setState(rdvDto.getState());
			UserResponse medecinResponse = new UserResponse();
			medecinResponse.setEmail(rdvDto.getMedecin().getEmail());
			medecinResponse.setFirstName(rdvDto.getMedecin().getFirstName());
			medecinResponse.setLastName(rdvDto.getMedecin().getLastName());
			medecinResponse.setUserID(rdvDto.getMedecin().getUserID());
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setName(rdvDto.getMedecin().getRole().getName());
			roleResponse.setRoleId(rdvDto.getMedecin().getRole().getRoleId());
			medecinResponse.setRole(roleResponse);
			rdvResponse.setMedecin(medecinResponse);
			PatientResponse patientResponse = new PatientResponse();
			patientResponse.setAdresse(rdvDto.getPatient().getAdresse());
			patientResponse.setCin(rdvDto.getPatient().getCin());
			patientResponse.setNom(rdvDto.getPatient().getNom());
			patientResponse.setPatientId(rdvDto.getPatient().getPatientId());
			patientResponse.setprenom(rdvDto.getPatient().getPrenom());
			patientResponse.setTelephone(rdvDto.getPatient().getTelephone());
			rdvDto.getPatient().getUsers().stream().forEach(userDto -> {
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
			rdvResponse.setPatient(patientResponse);
			rdvsResponses.add(rdvResponse);
		}
		return new ResponseEntity<List<RdvResponse>>(rdvsResponses,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<RdvResponse>> getRdvs() throws Exception {
		List<RdvResponse> rdvResponses = new ArrayList<>();
		List<RdvDto> rdvDtos = rdvService.getRdvs();
		for(RdvDto rdvDto: rdvDtos) {
			ModelMapper modelMapper = new ModelMapper();
			RdvResponse rdvResponse = modelMapper.map(rdvDto, RdvResponse.class);
			rdvResponses.add(rdvResponse);
		}
		return new ResponseEntity<List<RdvResponse>>(rdvResponses,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateRdv(@PathVariable String id, @RequestBody RdvRequest rdvRequest) throws Exception{
		RdvDto rdvDto = new RdvDto();
		BeanUtils.copyProperties(rdvRequest, rdvDto);
		PatientDto patientDto = new PatientDto();
		rdvDto.setPatient(patientDto);
		rdvDto.getPatient().setPatientId(rdvRequest.getPatientId());
		UserDto userDto = new UserDto();
		rdvDto.setMedecin(userDto);
		rdvDto.getMedecin().setUserID(rdvRequest.getMedecinId());
		RdvDto updateRdv = rdvService.updateRdv(rdvDto, id);
		ModelMapper modelMapper = new ModelMapper();
		RdvResponse rdvResponse = modelMapper.map(updateRdv, RdvResponse.class);
		return new ResponseEntity<>(rdvResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/{rdvId}")
	public ResponseEntity<Object> deleteRdv(@PathVariable String rdvId) {
		rdvService.deleteRdv(rdvId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
