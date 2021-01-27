package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.Facture;

@Repository
public interface FactureRepository extends CrudRepository<Facture, Long>{

	public Facture findByFactureId(String factureId);
	
}
