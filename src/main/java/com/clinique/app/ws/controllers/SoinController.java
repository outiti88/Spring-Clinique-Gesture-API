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

import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.requests.SoinRequest;
import com.clinique.app.ws.responses.SoinResponse;
import com.clinique.app.ws.services.SoinService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController 
@RequestMapping("/soin")
public class SoinController {
	
	@Autowired
	SoinService soinService;
	
	@PostMapping
	public ResponseEntity<Object> createSoin(@RequestBody @Valid SoinRequest soinRequest) throws Exception{
		SoinDto soinDto = new SoinDto();
		soinDto.setPrix(soinRequest.getPrix());
		soinDto.setTypeSoin(soinRequest.getTypeSoin());
		UserDto medecin = new UserDto();
		medecin.setUserID(soinRequest.getMedecinId());
		soinDto.setMedecin(medecin);
		soinDto = soinService.addSoin(soinDto);
		ModelMapper modelMapper = new ModelMapper();
		SoinResponse soinResponse = modelMapper.map(soinDto, SoinResponse.class);
		return new ResponseEntity<>(soinResponse, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{soinId}")
	public ResponseEntity<Object> updateSoin(@PathVariable String soinId, @RequestBody @Valid SoinRequest soinRequest){
		SoinDto soinDto = new SoinDto();
		soinDto.setPrix(soinRequest.getPrix());
		soinDto.setTypeSoin(soinRequest.getTypeSoin());
		UserDto medecin = new UserDto();
		medecin.setUserID(soinRequest.getMedecinId());
		soinDto.setMedecin(medecin);
		soinDto = soinService.updateSoin(soinDto, soinId);
		ModelMapper modelMapper = new ModelMapper();
		SoinResponse soinResponse = modelMapper.map(soinDto, SoinResponse.class);
		return new ResponseEntity<>(soinResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Object> getSoins(){
		List<SoinResponse> soinsResponses = new ArrayList<SoinResponse>();
		List<SoinDto> soinsDtos = soinService.getSoins();
		ModelMapper modelMapper = new ModelMapper();
		soinsDtos.stream().forEach(soinDto ->{
			SoinResponse soinResponse = new SoinResponse();
			soinResponse = modelMapper.map(soinDto, SoinResponse.class);
			soinsResponses.add(soinResponse);
		});
		return new ResponseEntity<>(soinsResponses,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{soinId}")
	public ResponseEntity<Object> deleteSoin(@PathVariable String soinId){
		soinService.deleteSoin(soinId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
