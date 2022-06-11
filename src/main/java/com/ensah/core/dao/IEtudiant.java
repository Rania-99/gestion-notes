package com.ensah.core.dao;

import com.ensah.core.bo.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEtudiant extends JpaRepository<Etudiant,Long> {
    Etudiant findByIdUtilisateur(long id);
}
