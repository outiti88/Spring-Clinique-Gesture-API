package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.clinique.app.ws.entities.SoinEntity;

@Repository
public interface SoinRepository extends CrudRepository<SoinEntity, Long> {
	
	SoinEntity findBySoinId(String soinId);
	
}
