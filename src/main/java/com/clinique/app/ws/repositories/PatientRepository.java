package com.clinique.app.ws.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {
	
	List<PatientEntity> findByUser(UserEntity currentUser);

}
