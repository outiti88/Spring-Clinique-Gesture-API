package com.clinique.app.ws.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.RdvDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.requests.RdvRequest;
import com.clinique.app.ws.responses.RdvResponse;
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
