package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.ScannerEntity;

@Repository
public interface ScannerRepository extends CrudRepository<ScannerEntity, Long>{

	public ScannerEntity findByScannerId(String scannerId);
	
}
