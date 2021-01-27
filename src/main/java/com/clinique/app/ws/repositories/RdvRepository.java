package com.clinique.app.ws.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.clinique.app.ws.entities.RdvEntity;
import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface RdvRepository extends CrudRepository<RdvEntity, Long> {
	
	RdvEntity findByRdvId(String rdvId);
	
	List<RdvEntity> findByMedecin(UserEntity medecin);
	
}
