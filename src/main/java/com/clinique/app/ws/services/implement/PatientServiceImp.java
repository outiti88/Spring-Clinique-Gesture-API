package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.PatientRepository;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.PatientService;
import com.clinique.app.ws.shared.Utils;

@Service
public class PatientServiceImp implements PatientService {

	
	@Autowired
	PatientRepository patientRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils util;
	
	@Override
	public List<PatientDto> getAllPatients(String userId) {
		List<PatientEntity> patients = (List<PatientEntity>) patientRepo.findAll();
		List<PatientDto> patientsDtos = new ArrayList<>();
		patients.stream().forEach(patient -> {
			PatientDto patientDto = mapEntityToDto(patient);
			patientsDtos.add(patientDto);
		});
		return patientsDtos;
	}

	@Override
	public PatientDto updatePatient(PatientDto patientDto, String patientId) {
		PatientEntity patientEntity = patientRepo.findByPatientId(patientId);
		if(patientEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		patientEntity.getUsers().clear();
		mapDtoToEntity(patientDto, patientEntity);
		patientDto = mapEntityToDto(patientEntity);

		patientRepo.save(patientEntity);
		return patientDto;
	}

	@Override
	public void deletePatient(String patientId) {
		PatientEntity patientEntity = patientRepo.findByPatientId(patientId);
		if(patientEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		patientRepo.delete(patientEntity);
		
	}
	
	private void mapDtoToEntity(PatientDto patientDto, PatientEntity patientEntity) {
		patientEntity.setNom(patientDto.getNom());
		patientEntity.setAdresse(patientDto.getAdresse());
		patientEntity.setCin(patientDto.getCin());
		patientEntity.setprenom(patientDto.getPrenom());
		if(patientEntity.getPatientId() == null ) {
			patientEntity.setPatientId(util.generateStringId(32));
		}
		patientEntity.setTelephone(patientDto.getTelephone());
		if (patientEntity.getUsers() == null) {
			patientEntity.setUsers(new HashSet<>());
		}
		patientDto.getUsers().stream().forEach(user -> {
			UserEntity userEntity = userRepository.findByUserID(user.getUserID());
			if (userEntity == null) {
				throw new UsernameNotFoundException(user.getUserID());
			}
			patientEntity.addUser(userEntity);
		});
	}
	
	private PatientDto mapEntityToDto(PatientEntity patientEntity) {
		ModelMapper modelMapper = new ModelMapper();
		PatientDto responseDto = new PatientDto();
		responseDto.setNom(patientEntity.getNom());
		responseDto.setAdresse(patientEntity.getAdresse());
		responseDto.setCin(patientEntity.getCin());
		responseDto.setPrenom(patientEntity.getprenom());
		responseDto.setTelephone(patientEntity.getTelephone());
		if (patientEntity.getPatientId() == null) {
			responseDto.setPatientId(util.generateStringId(32));
		}else {
			responseDto.setPatientId(patientEntity.getPatientId());
		}
		responseDto.setUsers(patientEntity.getUsers().stream().map(user -> {
			UserDto userDto = modelMapper.map(user, UserDto.class);
			return userDto;
		}).collect(Collectors.toSet()));
		return responseDto;
	}

	@Override
	public PatientDto addPatient(PatientDto patientDto) {
		PatientEntity patientEntity = new PatientEntity();
		mapDtoToEntity(patientDto, patientEntity);
		PatientEntity savedPatient = patientRepo.save(patientEntity);
		return mapEntityToDto(savedPatient);
	}
}
