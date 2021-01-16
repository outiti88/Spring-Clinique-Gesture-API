package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.MedicamentEntity;

@Repository
public interface MedicamentRepository extends CrudRepository<MedicamentEntity, Long>{

	public MedicamentEntity findByMedicamentId(String medicamentId);
	
}
