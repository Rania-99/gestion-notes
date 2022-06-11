package com.ensah.core.web.controllers;

import java.io.IOException;

import com.ensah.core.services.IImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {
    @RequestMapping("/import")
    public String homePage() {
        return "Import";
    }

    @Autowired
    private IImportService excel;

    @PostMapping("/import")
    public String upload(@RequestParam(value="file") MultipartFile inputfile, Model model) throws IOException {
    	
    	if(excel.Importer(model,inputfile)==false){
    	    return "Erreur";
    	}

    	excel.Insert(inputfile);
      return  "succes";
}
    }
