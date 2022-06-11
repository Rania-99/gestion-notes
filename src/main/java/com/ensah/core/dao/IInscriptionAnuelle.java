package com.ensah.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.core.bo.InscriptionAnnuelle;

public interface IInscriptionAnuelle extends JpaRepository<InscriptionAnnuelle,Long> {
  boolean existsByEtudiant_IdUtilisateur(long id);
  InscriptionAnnuelle findByEtudiant_IdUtilisateur(long id);

}
