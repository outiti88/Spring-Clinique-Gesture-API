package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.clinique.app.ws.entities.RdvEntity;

@Repository
public interface RdvRepository extends CrudRepository<RdvEntity, Long> {
	
	RdvEntity findByRdvId(String rdvId);
	
}
