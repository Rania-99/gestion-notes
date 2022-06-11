package com.ensah.core.dao;

import com.ensah.core.bo.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnseignant extends JpaRepository<Enseignant,Long> {
    boolean existsByIdUtilisateur(long id);
}
