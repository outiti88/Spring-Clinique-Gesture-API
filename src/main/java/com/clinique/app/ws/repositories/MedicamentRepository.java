package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.Medicament;

@Repository
public interface MedicamentRepository extends CrudRepository<Medicament, Long>{

	public Medicament findByMedicamentId(String medicamentId);
	
}
