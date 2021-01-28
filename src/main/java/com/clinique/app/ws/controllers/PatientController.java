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
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.PatientRepository;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.requests.PatientRequest;
import com.clinique.app.ws.responses.PatientResponse;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.PatientService;
import com.clinique.app.ws.services.UserService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/patients")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	UserRepository userRepository ;
	
	@GetMapping
	public ResponseEntity<List<PatientResponse>> getPatients(Principal principal) {
		
		//UserDto currentUser = userService.getUserByUserId(principal.getName());
		//if(currentUser.getRole().getName().equals("gp") || currentUser.getRole().getName().equals("medecin")) {
			List<PatientDto> patients = patientService.getAllPatients(principal.getName());
			
			Type listType = new TypeToken<List<PatientResponse>>() {}.getType();
			List<PatientResponse> patientResponse = new ModelMapper().map(patients, listType);
			
			return new ResponseEntity<List<PatientResponse>>(patientResponse, HttpStatus.OK);
		//}
		
		//return new ResponseEntity<List<PatientResponse>>(HttpStatus.UNAUTHORIZED);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> createPatient(@RequestBody @Valid PatientRequest patientRequest, Principal principal) throws Exception {

		//UserDto currentUser = userService.getUserByUserId(principal.getName());
		//if(currentUser.getRole().getName().equals("gp") || currentUser.getRole().getName().equals("medecin") ) {
			if( patientRepository.findByCin(patientRequest.getCin()) != null ) throw new UserException(ErrorMessages.REQUIRED_ALREADY_EXISTS.getErrorMessage());
			ModelMapper modelMapper = new ModelMapper();
			PatientDto patientDto = modelMapper.map(patientRequest, PatientDto.class);
			patientDto.getUsers().clear();
			patientRequest.getUsersIds().stream().forEach(userId -> {
				UserDto userDto = new UserDto();
				userDto.setUserID(userId);
				patientDto.getUsers().add(userDto);
			});
			
			PatientDto createPatient = patientService.addPatient(patientDto); //Appel au service
			
			PatientResponse patientResponse = modelMapper.map(createPatient, PatientResponse.class);
			
			return new ResponseEntity<>(patientResponse,HttpStatus.CREATED);
		//}
		//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping(path = "/{patientId}")
	public ResponseEntity<Object> updatePatient(@RequestBody @Valid PatientRequest patientRequest, Principal principal, @PathVariable String patientId) throws Exception {
		//UserDto currentUser = userService.getUserByUserId(principal.getName());
		//if(currentUser.getRole().getName().equals("gp") || currentUser.getRole().getName().equals("medecin")) {
			ModelMapper modelMapper = new ModelMapper();
			PatientDto patientDto = modelMapper.map(patientRequest, PatientDto.class);
			patientDto.getUsers().clear();
			patientRequest.getUsersIds().stream().forEach(userId -> {
				UserDto userDto = new UserDto();
				userDto.setUserID(userId);
				patientDto.getUsers().add(userDto);
			});
			PatientDto responseDto = patientService.updatePatient(patientDto, patientId);//Appel au service
			
			PatientResponse patientResponse = modelMapper.map(responseDto, PatientResponse.class);
			
			return new ResponseEntity<>(patientResponse,HttpStatus.CREATED) ;
		//}
		//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping(path = "/{patientId}")
	public ResponseEntity<Object> deletePatient(Principal principal , @PathVariable String patientId) {
		
		//UserDto currentUser = userService.getUserByUserId(principal.getName());
		//if(currentUser.getRole().getName().equals("gp") || currentUser.getRole().getName().equals("medecin")) {
			patientService.deletePatient(patientId);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		//}
		//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
	}
}
