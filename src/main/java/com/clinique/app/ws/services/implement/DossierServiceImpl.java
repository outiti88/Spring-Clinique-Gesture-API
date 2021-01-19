package com.clinique.app.ws.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.DosMedDto;
import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.dto.RoleDto;
import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.Dossier;
import com.clinique.app.ws.entities.DossierMedicament;
import com.clinique.app.ws.entities.Medicament;
import com.clinique.app.ws.entities.ScannerEntity;
import com.clinique.app.ws.entities.SoinEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.DossierMedicamentRepository;
import com.clinique.app.ws.repositories.DossierRepository;
import com.clinique.app.ws.repositories.MedicamentRepository;
import com.clinique.app.ws.repositories.ScannerRepository;
import com.clinique.app.ws.repositories.SoinRepository;
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
	ScannerRepository scannerRepository;
	
	@Autowired
	SoinRepository soinRepository;
	
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
		dossierRepository.delete(dossier);
	}
	
	private Dossier mapDtoToEntity(DossierDto dossierDto) {
		Dossier dossier = new Dossier();
		dossier.getMedicaments().clear();
		if (dossierDto.getDossierId() == null) {
			dossier.setDossierId(util.generateStringId(32));
		}else {
			dossier.setDossierId(dossierDto.getDossierId());
		}
		this.dossierMedicament.clear();
		dossierDto.getDosMedDtos().stream().forEach(dosMedDto -> {
			Medicament medicament = medicamentRepository.findByMedicamentId(dosMedDto.getMedicamentDto().getMedicamentId());
			DossierMedicament dossierMedicament = dossier.addMedicament(medicament, dosMedDto.getQty());
			this.dossierMedicament.add(dossierMedicament);
		});
		dossierDto.getScannersDtos().stream().forEach(scannerDto -> {
			ScannerEntity scanner = scannerRepository.findByScannerId(scannerDto.getScannerId());
			dossier.getScanners().add(scanner);
		});
		dossierDto.getSoinsDtos().stream().forEach(soinDto -> {
			SoinEntity soin = soinRepository.findBySoinId(soinDto.getSoinId());
			dossier.getSoins().add(soin);
		});
		return dossier;
	}
	
	private DossierDto mapEntityToDto(Dossier dossier) {
		DossierDto dossierDto = new DossierDto();
		dossierDto.setDossierId(dossier.getDossierId());
		dossierDto.getDosMedDtos().clear();
		dossier.getMedicaments().stream().forEach(dossierMedicament ->{
			DosMedDto dosMedDto = new DosMedDto();
			dosMedDto.getMedicamentDto().setCategory(dossierMedicament.getMedicament().getCategory());
			dosMedDto.getMedicamentDto().setMedicamentId(dossierMedicament.getMedicament().getMedicamentId());
			dosMedDto.getMedicamentDto().setName(dossierMedicament.getMedicament().getName());
			dosMedDto.getMedicamentDto().setPrice(dossierMedicament.getMedicament().getPrice());
			dosMedDto.getMedicamentDto().setType(dossierMedicament.getMedicament().getType());
			dosMedDto.setQty(dossierMedicament.getQty());
			dossierDto.getDosMedDtos().add(dosMedDto);
		});
		dossier.getScanners().stream().forEach(scanner -> {
			ScannerDto scannerDto = new ScannerDto();
			scannerDto.setName(scanner.getName());
			scannerDto.setPrice(scanner.getPrice());
			scannerDto.setScannerId(scanner.getScannerId());
			dossierDto.getScannersDtos().add(scannerDto);
		});
		dossier.getSoins().stream().forEach(soin -> {
			SoinDto soinDto = new SoinDto();
			UserDto userDto = new UserDto();
			userDto.setEmail(soin.getMedecin().getEmail());
			userDto.setFirstName(soin.getMedecin().getFirstName());
			userDto.setLastName(soin.getMedecin().getLastName());
			userDto.setUserID(soin.getMedecin().getUserID());
			RoleDto roleDto = new RoleDto();
			roleDto.setRoleId(soin.getMedecin().getRole().getRoleId());
			roleDto.setName(soin.getMedecin().getRole().getName());
			userDto.setRole(roleDto);
			soinDto.setMedecin(userDto);
			soinDto.setPrix(soin.getPrix());
			soinDto.setSoinId(soin.getSoinId());
			soinDto.setTypeSoin(soin.getTypeSoin());
			dossierDto.getSoinsDtos().add(soinDto);
		});
		return dossierDto;
	}

}
