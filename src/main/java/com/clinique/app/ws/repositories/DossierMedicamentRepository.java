package com.clinique.app.ws.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clinique.app.ws.entities.DossierMedicament;

@Repository
public interface DossierMedicamentRepository  extends CrudRepository<DossierMedicament, Long>{
	
	@Query("Select d from DossierMedicament d WHERE d.dossier.id = :dossierId and d.medicament.id = :medicamentId")
	public DossierMedicament findByDossierAndMedicament(
			@Param("dossierId") Long dossierId,
			@Param("medicamentId") Long medicamentId
			);
	
	@Transactional
	@Modifying
	@Query("delete from DossierMedicament d where d.medicament.id = :medicamentId")
	public void deleteDossierMedicamentByMedicamentId(@Param("medicamentId") Long medicamentId);
}
