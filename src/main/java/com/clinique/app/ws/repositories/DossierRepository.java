package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.Dossier;

@Repository
public interface DossierRepository  extends CrudRepository<Dossier, Long>{
	
	public Dossier findByDossierId(String dossierId);

}
