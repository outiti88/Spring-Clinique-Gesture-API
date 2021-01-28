package com.clinique.app.ws.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clinique.app.ws.entities.RdvEntity;
import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface RdvRepository extends CrudRepository<RdvEntity, Long> {
	
	RdvEntity findByRdvId(String rdvId);
	
	List<RdvEntity> findByMedecin(UserEntity medecin);
	
	@Query(value="Select * from rdv_entity where date like :date and start_time like :startTime and end_time like :endTime and motif like :motif and state like :state", nativeQuery = true)
	List<RdvEntity> filterRdv(
			@Param("date") String date,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime,
			@Param("motif") String motif,
			@Param("state") String state
			);
	
	@Transactional
	@Modifying
	@Query(value="delete from rdv_entity where rdv_id = :rdvId", nativeQuery = true)
	public void deleteRdvByRdvId(@Param("rdvId") String rdvId);
}
