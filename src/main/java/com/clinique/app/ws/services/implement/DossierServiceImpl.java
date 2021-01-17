package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.entities.Dossier;
import com.clinique.app.ws.entities.DossierMedicament;
import com.clinique.app.ws.entities.Medicament;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.DossierMedicamentRepository;
import com.clinique.app.ws.repositories.DossierRepository;
import com.clinique.app.ws.repositories.MedicamentRepository;
import com.clinique.app.ws.requests.DosMedRequest;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.DossierService;
import com.clinique.app.ws.shared.Utils;

@Service
public class DossierServiceImpl implements DossierService{
	
	@Autowired
	DossierRepository dossierRepository;
	
	@Autowired
	MedicamentRepository medicamentRepository;
	
	@Autowired
	DossierMedicamentRepository dossierMedicamentRepository;
	
	@Autowired
	Utils util;
	
	List<DossierMedicament> dossierMedicament = new ArrayList<>();

	@Override
	public DossierDto addDossier(DossierDto dossierDto) {
		Dossier dossier = mapDtoToEntity(dossierDto);
		Dossier createdDossier = dossierRepository.save(dossier);
		this.dossierMedicament.stream().forEach(dm -> {
			dm.getDossier().setId(createdDossier.getId());
			dossierMedicamentRepository.save(dm);
		});
		dossierDto = mapEntityToDto(dossier);
		return dossierDto;
	}

	@Override
	public DossierDto updateDossier(DossierDto dossierDto, String dossierId) {
		Dossier dossier = dossierRepository.findByDossierId(dossierId);
		if (dossier == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		Long id = dossier.getId();
		dossier = mapDtoToEntity(dossierDto);
		dossier.setId(id);
		Dossier updatedDossier = dossierRepository.save(dossier);
		this.dossierMedicament.stream().forEach(dm -> {
			dm.getDossier().setId(updatedDossier.getId());
			dossierMedicamentRepository.save(dm);
		});
		dossierDto = mapEntityToDto(dossier);
		return dossierDto;
	}

	@Override
	public List<DossierDto> getDossiers() {
		List<Dossier> dossiers = (List<Dossier>) dossierRepository.findAll();
		List<DossierDto> dossiersDtos = new ArrayList<DossierDto>();
		dossiers.stream().forEach(dossier -> {
			dossiersDtos.add(mapEntityToDto(dossier));
		});
		return dossiersDtos;
	}

	@Override
	public void deleteDossier(String dossierId) {
		Dossier dossier = dossierRepository.findByDossierId(dossierId);
		if (dossier == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		//dossier.getMedicaments().stream().forEach(dossierMedicament -> {
			//dossierMedicamentRepository.delete(dossierMedicament);
		//});
		dossierRepository.delete(dossier);
	}
	
	private Dossier mapDtoToEntity(DossierDto dossierDto) {
		Dossier dossier = new Dossier();
		dossier.getMedicaments().clear();
		dossier.setDossierId(util.generateStringId(32));
		dossierDto.getDosMedRequest().stream().forEach(dosMedRequest -> {
			Medicament medicament = medicamentRepository.findByMedicamentId(dosMedRequest.getMedicamentId());
			DossierMedicament dossierMedicament = dossier.addMedicament(medicament, dosMedRequest.getQty());
			this.dossierMedicament.add(dossierMedicament);
		});
		return dossier;
	}
	
	private DossierDto mapEntityToDto(Dossier dossier) {
		DossierDto dossierDto = new DossierDto();
		dossierDto.setDossierId(dossier.getDossierId());
		dossierDto.getDosMedRequest().clear();
		dossier.getMedicaments().stream().forEach(dossierMedicament ->{
			DosMedRequest dosMedRequest = new DosMedRequest();
			dosMedRequest.setMedicamentId(dossierMedicament.getMedicament().getMedicamentId());
			dosMedRequest.setQty(dossierMedicament.getQty());
			dossierDto.getDosMedRequest().add(dosMedRequest);
		});
		return dossierDto;
	}

}
