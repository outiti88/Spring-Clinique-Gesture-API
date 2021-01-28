package com.clinique.app.ws.services.implement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.RdvDto;
import com.clinique.app.ws.dto.RoleDto;
import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.Dossier;
import com.clinique.app.ws.entities.Medicament;
import com.clinique.app.ws.entities.RdvEntity;
import com.clinique.app.ws.entities.ScannerEntity;
import com.clinique.app.ws.entities.SoinEntity;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.DossierRepository;
import com.clinique.app.ws.repositories.MedicamentRepository;
import com.clinique.app.ws.repositories.RdvRepository;
import com.clinique.app.ws.repositories.ScannerRepository;
import com.clinique.app.ws.repositories.SoinRepository;
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
	ScannerRepository scannerRepository;
	
	@Autowired
	RdvRepository rdvRepository;
	
	@Autowired
	SoinRepository soinRepository;
	
	@Autowired
	Utils util;

	@Override
	public DossierDto addDossier(DossierDto dossierDto) {
		Dossier dossier = mapDtoToEntity(dossierDto);
		dossier.getRdv().setDossier(dossier);
		RdvEntity createdRdv = rdvRepository.save(dossier.getRdv());
		//Dossier createdDossier = dossierRepository.save(dossier);
		dossierDto = mapEntityToDto(createdRdv.getDossier());
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
		dossierDto = mapEntityToDto(updatedDossier);
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
		if (dossier.getRdv() != null) {
			RdvEntity rdvEntity = rdvRepository.findByRdvId(dossier.getRdv().getRdvId());
			rdvEntity.setDossier(null);
			rdvRepository.save(rdvEntity);
		}
		if (dossier.getFacture() != null) {
			dossier.setFacture(null);
			dossierRepository.save(dossier);
		}
		dossierRepository.delete(dossier);
	}
	
	private Dossier mapDtoToEntity(DossierDto dossierDto) {
		Dossier dossier = new Dossier();
		RdvEntity rdvEntity = rdvRepository.findByRdvId(dossierDto.getRdvDto().getRdvId());
		dossier.setRdv(rdvEntity);
		dossier.getMedicaments().clear();
		if (dossierDto.getDossierId() == null) {
			dossier.setDossierId(util.generateStringId(32));
		}else {
			dossier.setDossierId(dossierDto.getDossierId());
		}
		dossierDto.getMedicamentsDtos().stream().forEach(medicamentDto -> {
			Medicament medicament = medicamentRepository.findByMedicamentId(medicamentDto.getMedicamentId());
			dossier.getMedicaments().add(medicament);
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		ModelMapper modelMapper = new ModelMapper();
		if (dossier.getRdv() != null) {
			RdvDto rdvDto = new RdvDto();
			rdvDto.setDate(dateFormat.format(dossier.getRdv().getDate()));
			rdvDto.setEndTime(timeFormat.format(dossier.getRdv().getEndTime()));
			rdvDto.setMotif(dossier.getRdv().getMotif());
			rdvDto.setRdvId(dossier.getRdv().getRdvId());
			rdvDto.setStartTime(timeFormat.format(dossier.getRdv().getStartTime()));
			rdvDto.setState(dossier.getRdv().getState().toString());
			rdvDto.setMedecin(modelMapper.map(dossier.getRdv().getMedecin(), UserDto.class));
			rdvDto.setPatient(modelMapper.map(dossier.getRdv().getPatient(), PatientDto.class));
			dossierDto.setRdvDto(rdvDto);
		}
		dossierDto.setDossierId(dossier.getDossierId());
		dossier.getMedicaments().stream().forEach(medicament ->{
			MedicamentDto medicamentDto = new MedicamentDto();
			medicamentDto.setCategory(medicament.getCategory());
			medicamentDto.setMedicamentId(medicament.getMedicamentId());
			medicamentDto.setName(medicament.getName());
			medicamentDto.setPrice(medicament.getPrice());
			medicamentDto.setType(medicament.getType());
			dossierDto.getMedicamentsDtos().add(medicamentDto);
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
