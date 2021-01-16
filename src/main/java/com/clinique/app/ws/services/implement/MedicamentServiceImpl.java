package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.entities.MedicamentEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.MedicamentRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.MedicamentService;
import com.clinique.app.ws.shared.Utils;

@Service
public class MedicamentServiceImpl implements MedicamentService{
	
	@Autowired
	MedicamentRepository medicamentRepository;
	
	@Autowired
	Utils util;

	@Override
	public MedicamentDto addMedicament(MedicamentDto medicamentDto) {
		medicamentDto.setMedicamentId(util.generateStringId(32));
		MedicamentEntity medicamentEntity = mapDtoToEntity(medicamentDto);
		MedicamentEntity createdMedicamentEntity = medicamentRepository.save(medicamentEntity);
		MedicamentDto createdMedicamentDto = mapEntityToDto(createdMedicamentEntity);
		return createdMedicamentDto;
	}

	@Override
	public MedicamentDto updateMedicament(MedicamentDto medicamentDto, String medicamentId) {
		MedicamentEntity medicamentEntity = medicamentRepository.findByMedicamentId(medicamentId);
		if (medicamentEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		Long id = medicamentEntity.getId();
		medicamentDto.setMedicamentId(medicamentEntity.getMedicamentId());
		medicamentEntity = mapDtoToEntity(medicamentDto);
		medicamentEntity.setId(id);
		MedicamentEntity updatedMedicamentEntity = medicamentRepository.save(medicamentEntity);
		MedicamentDto updatedMedicamentDto = mapEntityToDto(updatedMedicamentEntity);
		return updatedMedicamentDto;
	}

	@Override
	public List<MedicamentDto> getMedicaments() {
		List<MedicamentEntity> medicamentsEntities = (List<MedicamentEntity>) medicamentRepository.findAll();
		List<MedicamentDto> medicamentsDtos = new ArrayList<MedicamentDto>();
		medicamentsEntities.stream().forEach(medicament -> {
			MedicamentDto medicamentDto = mapEntityToDto(medicament);
			medicamentsDtos.add(medicamentDto);
		});
		return medicamentsDtos;
	}

	@Override
	public void deleteMedicament(String medicamentId) {
		MedicamentEntity medicamentEntity = medicamentRepository.findByMedicamentId(medicamentId);
		if (medicamentEntity == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		medicamentRepository.delete(medicamentEntity);
	}
	
	private MedicamentEntity mapDtoToEntity(MedicamentDto medicamentDto) {
		MedicamentEntity medicamentEntity = new MedicamentEntity();
		medicamentEntity.setCategory(medicamentDto.getCategory());
		medicamentEntity.setName(medicamentDto.getName());
		medicamentEntity.setPrice(medicamentDto.getPrice());
		medicamentEntity.setType(medicamentDto.getType());
		medicamentEntity.setMedicamentId(medicamentDto.getMedicamentId());
		return medicamentEntity;
	}
	
	private MedicamentDto mapEntityToDto(MedicamentEntity medicamentEntity) {
		MedicamentDto medicamentDto = new MedicamentDto();
		medicamentDto.setCategory(medicamentEntity.getCategory());
		medicamentDto.setMedicamentId(medicamentEntity.getMedicamentId());
		medicamentDto.setName(medicamentEntity.getName());
		medicamentDto.setPrice(medicamentEntity.getPrice());
		medicamentDto.setType(medicamentEntity.getType());
		return medicamentDto;
	}

}
