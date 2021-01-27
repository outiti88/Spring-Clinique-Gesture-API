package com.clinique.app.ws.services.implement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.DossierDto;
import com.clinique.app.ws.dto.FactureDto;
import com.clinique.app.ws.dto.MedicamentDto;
import com.clinique.app.ws.dto.PatientDto;
import com.clinique.app.ws.dto.RdvDto;
import com.clinique.app.ws.dto.RoleDto;
import com.clinique.app.ws.dto.ScannerDto;
import com.clinique.app.ws.dto.SoinDto;
import com.clinique.app.ws.dto.UserDto;
import com.clinique.app.ws.entities.Dossier;
import com.clinique.app.ws.entities.Facture;
import com.clinique.app.ws.exception.UserException;
import com.clinique.app.ws.repositories.DossierRepository;
import com.clinique.app.ws.repositories.FactureRepository;
import com.clinique.app.ws.responses.errors.ErrorMessages;
import com.clinique.app.ws.services.FactureService;
import com.clinique.app.ws.shared.Utils;

@Service
public class FactureServiceImpl implements FactureService{
	
	@Autowired
	FactureRepository factureRepository;
	
	@Autowired
	DossierRepository dossierRepository;
	
	@Autowired
	Utils util;

	@Override
	public FactureDto addFacture(FactureDto factureDto) {
		Facture facture = new Facture();
		facture = mapDtoToEntity(facture, factureDto);
		Facture createdFacture = factureRepository.save(facture);
		Iterator<Dossier> iterator = facture.getDossiers().iterator();
		while (iterator.hasNext()) {
			Dossier dossier = iterator.next();
			dossier.setFacture(facture);
			dossierRepository.save(dossier);
		}
		while (iterator.hasNext()) {
			Dossier dossier = iterator.next();
			facture.getDossiers().add(dossier);
		}
		factureDto = mapEntityToDto(createdFacture);
		return factureDto;
	}

	@Override
	public List<FactureDto> getFactures() {
		List<Facture> factures = (List<Facture>) factureRepository.findAll();
		List<FactureDto> facturesDtos = new ArrayList<FactureDto>();
		Iterator<Facture> iterator = factures.iterator();
		while (iterator.hasNext()) {
			Facture facture = iterator.next();
			List<Dossier> dossiers = (List<Dossier>) dossierRepository.findAll();
			FactureDto factureDto = new FactureDto();
			Iterator<Dossier> dossierIterator = dossiers.iterator();
			while (dossierIterator.hasNext()) {
				Dossier dossier = dossierIterator.next();
				if (dossier.getFacture().getId() == facture.getId() && !facture.getDossiers().contains(dossier)) {
					facture.getDossiers().add(dossier);
				}
			}
			factureDto = mapEntityToDto(facture);
			facturesDtos.add(factureDto);
		}
		return facturesDtos;
	}

	@Override
	public FactureDto updateFacture(FactureDto factureDto) {
		Facture facture = factureRepository.findByFactureId(factureDto.getFactureId());
		if (facture == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		facture = mapDtoToEntity(facture, factureDto);
		Facture updatedFacture = factureRepository.save(facture);
		Iterator<Dossier> iterator = facture.getDossiers().iterator();
		while (iterator.hasNext()) {
			Dossier dossier = iterator.next();
			dossier.setFacture(facture);
			dossierRepository.save(dossier);
		}
		while (iterator.hasNext()) {
			Dossier dossier = iterator.next();
			facture.getDossiers().add(dossier);
		}
		factureDto = mapEntityToDto(updatedFacture);
		return factureDto;
	}

	@Override
	public void deleteFacture(String factureId) {
		Facture facture = factureRepository.findByFactureId(factureId);
		if (facture == null)  throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		Iterator<Dossier> iterator = facture.getDossiers().iterator();
		while (iterator.hasNext()) {
			Dossier dossier = iterator.next();
			dossier.setFacture(null);
			dossierRepository.save(dossier);
		}
		factureRepository.delete(facture);
	}
	
	private Facture mapDtoToEntity(Facture facture, FactureDto factureDto) {
		if (factureDto.getFactureId() == null) {
			facture.setFactureId(util.generateStringId(32));
		}else {
			facture.setFactureId(factureDto.getFactureId());
		}
		facture.setPaid(factureDto.isPaid());
		facture.setTotalPrice(factureDto.getTotalPrice());
		facture.getDossiers().clear();
		Iterator<DossierDto> iterator = factureDto.getDossiersDtos().iterator();
		while (iterator.hasNext()) {
			DossierDto dossierDto = iterator.next();
			Dossier dossier = dossierRepository.findByDossierId(dossierDto.getDossierId());
			facture.getDossiers().add(dossier);
		}
		return facture;
	}
	
	private FactureDto mapEntityToDto(Facture facture) {
		FactureDto factureDto = new FactureDto();
		factureDto.setFactureId(facture.getFactureId());
		factureDto.setPaid(facture.isPaid());
		factureDto.setTotalPrice(facture.getTotalPrice());
		Iterator<Dossier> iterator = facture.getDossiers().iterator();
		while (iterator.hasNext()) {
			DossierDto dossierDto = new DossierDto();
			Dossier dossier = iterator.next();
			RdvDto rdvDto = new RdvDto();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat timeFormat = new SimpleDateFormat("HH:mm");
			ModelMapper modelMapper = new ModelMapper();
			rdvDto.setDate(dateFormat.format(dossier.getRdv().getDate()));
			rdvDto.setEndTime(timeFormat.format(dossier.getRdv().getEndTime()));
			rdvDto.setMotif(dossier.getRdv().getMotif());
			rdvDto.setRdvId(dossier.getRdv().getRdvId());
			rdvDto.setStartTime(timeFormat.format(dossier.getRdv().getStartTime()));
			rdvDto.setState(dossier.getRdv().getState().toString());
			rdvDto.setMedecin(modelMapper.map(dossier.getRdv().getMedecin(), UserDto.class));
			rdvDto.setPatient(modelMapper.map(dossier.getRdv().getPatient(), PatientDto.class));
			dossierDto.setRdvDto(rdvDto);
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
			factureDto.getDossiersDtos().add(dossierDto);
		}
		return factureDto;
	}

	
	
	
}
