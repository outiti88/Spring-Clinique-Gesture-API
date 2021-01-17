package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.entities.Medicament;
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
		Medicament medicament = mapDtoToEntity(medicamentDto);
		Medicament createdMedicamentEntity = medicamentRepository.save(medicament);
		MedicamentDto createdMedicamentDto = mapEntityToDto(createdMedicamentEntity);
		return createdMedicamentDto;
	}

	@Override
	public MedicamentDto updateMedicament(MedicamentDto medicamentDto, String medicamentId) {
		Medicament medicament = medicamentRepository.findByMedicamentId(medicamentId);
		if (medicament == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		Long id = medicament.getId();
		medicamentDto.setMedicamentId(medicament.getMedicamentId());
		medicament = mapDtoToEntity(medicamentDto);
		medicament.setId(id);
		Medicament updatedMedicamentEntity = medicamentRepository.save(medicament);
		MedicamentDto updatedMedicamentDto = mapEntityToDto(updatedMedicamentEntity);
		return updatedMedicamentDto;
	}

	@Override
	public List<MedicamentDto> getMedicaments() {
		List<Medicament> medicamentsEntities = (List<Medicament>) medicamentRepository.findAll();
		List<MedicamentDto> medicamentsDtos = new ArrayList<MedicamentDto>();
		medicamentsEntities.stream().forEach(medicament -> {
			MedicamentDto medicamentDto = mapEntityToDto(medicament);
			medicamentsDtos.add(medicamentDto);
		});
		return medicamentsDtos;
	}

	@Override
	public void deleteMedicament(String medicamentId) {
		Medicament medicament = medicamentRepository.findByMedicamentId(medicamentId);
		if (medicament == null) throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		medicamentRepository.delete(medicament);
	}
	
	private Medicament mapDtoToEntity(MedicamentDto medicamentDto) {
		Medicament medicament = new Medicament();
		medicament.setCategory(medicamentDto.getCategory());
		medicament.setName(medicamentDto.getName());
		medicament.setPrice(medicamentDto.getPrice());
		medicament.setType(medicamentDto.getType());
		medicament.setMedicamentId(medicamentDto.getMedicamentId());
		return medicament;
	}
	
	private MedicamentDto mapEntityToDto(Medicament medicament) {
		MedicamentDto medicamentDto = new MedicamentDto();
		medicamentDto.setCategory(medicament.getCategory());
		medicamentDto.setMedicamentId(medicament.getMedicamentId());
		medicamentDto.setName(medicament.getName());
		medicamentDto.setPrice(medicament.getPrice());
		medicamentDto.setType(medicament.getType());
		return medicamentDto;
	}

}
