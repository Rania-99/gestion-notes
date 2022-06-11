package com.ensah.core.services;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImportService {
	boolean Importer(Model model, MultipartFile inputfile) throws IOException;
	void Insert(MultipartFile inputfile) throws IOException;


}