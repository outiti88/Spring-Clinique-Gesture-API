package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.PatientEntity;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
	
	PatientEntity findByPatientId(String patientId);
	
	PatientEntity findByCin(String cin);

}
