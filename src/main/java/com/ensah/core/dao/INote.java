package com.ensah.core.dao;

import com.ensah.core.bo.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INote extends JpaRepository<Note, Long> {

}
