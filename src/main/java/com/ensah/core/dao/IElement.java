package com.ensah.core.dao;
import com.ensah.core.bo.Element;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IElement extends JpaRepository<Element,Long> {


    List<Element> getAllByModule_IdModule(long id);
    boolean existsByNom(String nom);
    Element findByNom(String nom);
}
