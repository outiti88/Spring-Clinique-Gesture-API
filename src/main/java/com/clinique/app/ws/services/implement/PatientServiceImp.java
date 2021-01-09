package com.clinique.app.ws.services.implement;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.repositories.PatientRepository;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.services.PatientService;
import com.clinique.app.ws.shared.Utils;

@Service
public class PatientServiceImp implements PatientService {

	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils util;
	
	@Override
	public List<PatientDto> getAllPatients(String userId) {
		
		UserEntity currentUser = userRepository.findByUserID(userId);
		
		
		List<PatientEntity> patients = currentUser.getRole().getName().equals("medecin") ?
				patientRepository.findByUser(currentUser) :
				
				(List<PatientEntity>) patientRepository.findAll() ;
		
		Type listType = new TypeToken<List<PatientDto>>() {}.getType();
		List<PatientDto> patientDto = new ModelMapper().map(patients, listType);
		
		
		return patientDto;
	}

	@Override
	public PatientDto createPatient(PatientDto patientDto, String medecin_id) {

		UserEntity medecin = userRepository.findByUserID(medecin_id);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(medecin,UserDto.class);
		
		patientDto.setPatientId(util.generateStringId(30));
		patientDto.setUser(userDto);
		
		PatientEntity patientEntity = modelMapper.map(patientDto,PatientEntity.class);
		PatientEntity newPatient = patientRepository.save(patientEntity);
		
		PatientDto patient = modelMapper.map(newPatient,PatientDto.class);

		return patient;
	}

	@Override
	public void deletePatient(String userId , String patientId) {
		
		UserEntity currentUser = userRepository.findByUserID(userId); //Lire l'user connecté

		
		PatientEntity patientEntity = currentUser.getRole().getName().equals("medecin") ?
				patientRepository.findPatientByMedecin(patientId, currentUser.getId()) :
				patientRepository.findByPatientId(patientId);
				
		if(patientEntity == null) throw new UsernameNotFoundException(patientId);
		
		patientRepository.delete(patientEntity);
	}

	@Override
	public PatientDto updatePatient(String patientId, PatientDto patientDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
