package com.ensah.core.dao;
import com.ensah.core.bo.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModule extends JpaRepository<Module,Long>,IGenericJPA<Module, Long> {



      Module findByTitre(String name);
      boolean existsByTitre(String titre);

}
