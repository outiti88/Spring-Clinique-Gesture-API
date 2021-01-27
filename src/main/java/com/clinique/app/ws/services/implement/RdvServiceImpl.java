package com.clinique.app.ws.services.implement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.RdvDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.RdvEntity;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.DossierRepository;
import com.clinique.app.ws.repositories.PatientRepository;
import com.clinique.app.ws.repositories.RdvRepository;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.RdvService;
import com.clinique.app.ws.shared.State;
import com.clinique.app.ws.shared.Utils;

@Service
public class RdvServiceImpl implements RdvService {
	
	@Autowired
	RdvRepository rdvRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PatientRepository patientRepository;

	@Autowired
	Utils util;
	
	@Override
	public RdvDto createRdv(RdvDto rdvDto) {
		rdvDto.setRdvId(util.generateStringId(32));
		UserEntity medecinEntity = userRepository.findByUserID(rdvDto.getMedecin().getUserID());
		if(medecinEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		PatientEntity patientEntity = patientRepository.findByPatientId(rdvDto.getPatient().getPatientId());
		if(patientEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		RdvEntity rdvEntity = mapDtoToEntity(rdvDto);
		rdvEntity.setMedecin(medecinEntity);
		rdvEntity.setPatient(patientEntity);
		RdvEntity createdRdvEntity = rdvRepository.save(rdvEntity);
		RdvDto createdRdvDto = mapEntityToDto(createdRdvEntity);
		return createdRdvDto;
	}
	
	
	private RdvEntity mapDtoToEntity(RdvDto rdvDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		Date startTime;
		Date endTime;
		RdvEntity rdvEntity = new RdvEntity();
		try {
			date = dateFormat.parse(rdvDto.getDate());
			startTime = timeFormat.parse(rdvDto.getDate() +" " + rdvDto.getStartTime());
			endTime = timeFormat.parse(rdvDto.getDate() +" " + rdvDto.getEndTime());
			rdvEntity.setDate(date);
			rdvEntity.setStartTime(startTime);
			rdvEntity.setEndTime(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rdvEntity.setMotif(rdvDto.getMotif());
		rdvEntity.setRdvId(rdvDto.getRdvId());	
		rdvEntity.setState(State.valueOf(rdvDto.getState()));
		return rdvEntity;
	}
	
	private RdvDto mapEntityToDto(RdvEntity rdvEntity) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		ModelMapper modelMapper = new ModelMapper();
		RdvDto rdvDto = new RdvDto();
		String date = dateFormat.format(rdvEntity.getDate());
		String startTime = timeFormat.format(rdvEntity.getStartTime());
		String endTime = timeFormat.format(rdvEntity.getEndTime());
		rdvDto.setDate(date);
		rdvDto.setEndTime(endTime);
		rdvDto.setRdvId(rdvEntity.getRdvId());
		rdvDto.setMedecin(modelMapper.map(rdvEntity.getMedecin(), UserDto.class));
		rdvDto.setPatient(modelMapper.map(rdvEntity.getPatient(), PatientDto.class));
		rdvDto.setStartTime(startTime);
		rdvDto.setMotif(rdvEntity.getMotif());
		rdvDto.setState(rdvEntity.getState().toString());
		return rdvDto;
	}


	@Override
	public RdvDto updateRdv(RdvDto rdvDto, String rdvId) {
		RdvEntity rdvEntity = rdvRepository.findByRdvId(rdvId);
		if (rdvEntity == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		rdvDto.setRdvId(rdvId);
		long id = rdvEntity.getId();
		UserEntity medecinEntity = userRepository.findByUserID(rdvDto.getMedecin().getUserID());
		if(medecinEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		PatientEntity patientEntity = patientRepository.findByPatientId(rdvDto.getPatient().getPatientId());
		if(patientEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		rdvEntity = mapDtoToEntity(rdvDto);
		rdvEntity.setId(id);
		rdvEntity.setMedecin(medecinEntity);
		rdvEntity.setPatient(patientEntity);
		rdvRepository.save(rdvEntity);
		rdvDto = mapEntityToDto(rdvEntity);
		return rdvDto;
	}


	@Override
	public void deleteRdv(String rdvId) {
		RdvEntity rdvEntity = rdvRepository.findByRdvId(rdvId);
		if (rdvEntity == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		rdvRepository.delete(rdvEntity);
	}


	@Override
	public List<RdvDto> getRdvs() {
		List<RdvEntity> rdvs = (List<RdvEntity>) rdvRepository.findAll();
		List<RdvDto> rdvsDtos = new ArrayList<>();
		rdvs.stream().forEach(rdv -> {
			RdvDto rdvDto = mapEntityToDto(rdv);
			rdvsDtos.add(rdvDto);
		});
		return rdvsDtos;
	}


	@Override
	public List<RdvDto> getRdvsByMedecin(String medecinId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = userRepository.findByUserID(medecinId);
		List<RdvEntity> rdvs = rdvRepository.findByMedecin(userEntity);
		List<RdvDto> rdvsDtos = new ArrayList<>();
		Iterator<RdvEntity> iterator = rdvs.iterator();
		while (iterator.hasNext()) {
			RdvEntity rdvEntity = iterator.next();
			RdvDto rdvDto = new RdvDto();
			String date = dateFormat.format(rdvEntity.getDate());
			String startTime = timeFormat.format(rdvEntity.getStartTime());
			String endTime = timeFormat.format(rdvEntity.getEndTime());
			rdvDto.setDate(date);
			rdvDto.setEndTime(endTime);
			rdvDto.setRdvId(rdvEntity.getRdvId());
			rdvDto.setMedecin(modelMapper.map(rdvEntity.getMedecin(), UserDto.class));
			rdvDto.setPatient(modelMapper.map(rdvEntity.getPatient(), PatientDto.class));
			rdvDto.setStartTime(startTime);
			rdvDto.setMotif(rdvEntity.getMotif());
			rdvDto.setState(rdvEntity.getState().toString());
			rdvsDtos.add(rdvDto);
		}
		return rdvsDtos;
	}

}
