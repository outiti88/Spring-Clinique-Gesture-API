package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.SoinEntity;
import com.clinique.app.ws.entities.UserEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.DossierRepository;
import com.clinique.app.ws.repositories.SoinRepository;
import com.clinique.app.ws.repositories.UserRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.SoinService;
import com.clinique.app.ws.shared.Utils;

@Service
public class SoinServiceImpl implements SoinService {

	@Autowired
	SoinRepository soinRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DossierRepository dossierRepository;
	
	@Autowired
	Utils util;
	
	@Override
	public SoinDto addSoin(SoinDto soinDto) {
		soinDto.setSoinId(util.generateStringId(32));
		UserEntity medecinEntity = userRepository.findByUserID(soinDto.getMedecin().getUserID());
		if(medecinEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		SoinEntity soinEntity = mapDtoToEntity(soinDto);
		soinEntity.setMedecin(medecinEntity);
		SoinEntity createdSoin = soinRepository.save(soinEntity);
		SoinDto createdSoinDto = mapEntityToDto(createdSoin);
		return createdSoinDto;
	}

	@Override
	public SoinDto updateSoin(SoinDto soinDto, String soinId) {
		SoinEntity soinEntity = soinRepository.findBySoinId(soinId);
		if (soinEntity == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		long id = soinEntity.getId();
		soinDto.setSoinId(soinEntity.getSoinId());
		UserEntity medecinEntity = userRepository.findByUserID(soinDto.getMedecin().getUserID());
		if(medecinEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		soinEntity = mapDtoToEntity(soinDto);
		soinEntity.setId(id);
		soinEntity.setMedecin(medecinEntity);
		SoinEntity createdSoinEntity = soinRepository.save(soinEntity);
		SoinDto createdSoinDto = mapEntityToDto(createdSoinEntity);
		return createdSoinDto;
	}

	@Override
	public void deleteSoin(String soinId) {
		SoinEntity soinEntity = soinRepository.findBySoinId(soinId);
		if (soinEntity == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		soinEntity.getDossiers().stream().forEach(dossier -> {
			dossier.removeSoin(soinEntity);
			dossierRepository.save(dossier);
		});
		soinRepository.delete(soinEntity);
	}

	@Override
	public List<SoinDto> getSoins() {
		List<SoinDto> soinsDtos = new ArrayList<SoinDto>();
		List<SoinEntity> soins = new ArrayList<SoinEntity>();
		soins = (List<SoinEntity>) soinRepository.findAll();
		soins.stream().forEach(soin ->{
			SoinDto soinDto = new SoinDto();
			soinDto = mapEntityToDto(soin);
			soinsDtos.add(soinDto);
		});
		return soinsDtos;
	}
	
	private SoinEntity mapDtoToEntity(SoinDto soinDto) {
		SoinEntity soinEntity = new SoinEntity();
		soinEntity.setPrix(soinDto.getPrix());
		soinEntity.setSoinId(soinDto.getSoinId());
		soinEntity.setTypeSoin(soinDto.getTypeSoin());
		return soinEntity;
	}
	
	private SoinDto mapEntityToDto(SoinEntity soinEntity) {
		SoinDto soinDto = new SoinDto();
		ModelMapper modelMapper = new ModelMapper();
		soinDto.setMedecin(modelMapper.map(soinEntity.getMedecin(), UserDto.class));
		soinDto.setPrix(soinEntity.getPrix());
		soinDto.setSoinId(soinEntity.getSoinId());
		soinDto.setTypeSoin(soinEntity.getTypeSoin());
		return soinDto;
	}

}
