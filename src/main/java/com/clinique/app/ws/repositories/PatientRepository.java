package com.clinique.app.ws.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
	
	List<PatientEntity> findByUser(UserEntity currentUser);
	
	PatientEntity findByPatientId(String patientId);
	
	@Query(value = "SELECT * FROM patients p WHERE p.patient_id = ?1 AND p.users_id = ?2 " , nativeQuery = true)
	PatientEntity findPatientByMedecin(String patientId , Long currentUser);


}
