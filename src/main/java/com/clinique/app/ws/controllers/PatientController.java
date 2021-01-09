package com.clinique.app.ws.controllers;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.requests.PatientRequest;
import com.clinique.app.ws.responses.PatientResponse;
import com.clinique.app.ws.responses.UserResponse;
import com.clinique.app.ws.services.PatientService;
import com.clinique.app.ws.services.UserService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/patients")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService ;
	
	@GetMapping
	public ResponseEntity<List<PatientResponse>> getPatients(Principal principal) {

		List<PatientDto> patients = patientService.getAllPatients(principal.getName());
		
		Type listType = new TypeToken<List<PatientResponse>>() {}.getType();
		List<PatientResponse> patientResponse = new ModelMapper().map(patients, listType);
		
		return new ResponseEntity<List<PatientResponse>>(patientResponse, HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> createPatient(@RequestBody @Valid PatientRequest patientRequest) throws Exception {
		
		ModelMapper modelMapper = new ModelMapper();
		PatientDto patientDto = modelMapper.map(patientRequest, PatientDto.class);
		
		PatientDto createPatient = patientService.createPatient(patientDto, patientRequest.getMedecin_id()); //Appel au service
		
		PatientResponse patientResponse = modelMapper.map(createPatient, PatientResponse.class);
		
		return new ResponseEntity<>(patientResponse,HttpStatus.CREATED) ; 
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> updatePatient(Principal principal , @PathVariable String id, @RequestBody PatientRequest patientRequest) {
		
		UserDto currentUser = userService.getUserByUserId(principal.getName());
		
		if(!currentUser.getRole().getName().equals("admin")) {
			ModelMapper modelMapper = new ModelMapper();
			PatientDto patientDto = modelMapper.map(patientRequest, PatientDto.class);
			
			PatientDto updatePatient = patientService.updatePatient(id,patientDto); //Appel au service
			
			UserResponse userResponse = modelMapper.map(updatePatient, UserResponse.class); //Copier vers la reponse
			
			return new ResponseEntity<>(userResponse,HttpStatus.ACCEPTED) ; 
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED) ; 
		
	}
	
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deletePatient(Principal principal , @PathVariable String id) {
		
		UserDto currentUser = userService.getUserByUserId(principal.getName());
		if(!currentUser.getRole().getName().equals("amin")) {
			patientService.deletePatient(principal.getName(),id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT) ; 
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED) ; 
	}


}
