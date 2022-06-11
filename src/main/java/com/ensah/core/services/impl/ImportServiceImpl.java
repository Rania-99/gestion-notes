package com.ensah.core.services.impl;

import com.ensah.core.bo.*;
import com.ensah.core.bo.Module;
import com.ensah.core.dao.*;
import com.ensah.core.services.IImportService;
import com.ensah.core.services.IPersonService;
import com.ensah.core.utils.ExcelImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImportServiceImpl implements IImportService {
    @Autowired
    private IPersonService person;
    @Autowired
    private IEnseignant enseignant;
    @Autowired
    private IModule module;
    @Autowired
    private IElement element;
    @Autowired
    private IClasse iniveau;
    @Autowired
    private IInscriptionAnuelle IA;
    @Autowired
    private IEtudiant IE;
    @Autowired
    private IInscriptionMatiere IM;



    @Override
    public boolean Importer(Model model,MultipartFile inputfile) throws IOException {

        String nomE= ExcelImporter.getEnseignat(inputfile.getInputStream());
        String module1=ExcelImporter.getModule(inputfile.getInputStream());
        String classe=ExcelImporter.getClass(inputfile.getInputStream());
        List<String>listElemnt=ExcelImporter.getElements(inputfile.getInputStream(),4);
        String fullName=nomE;
        String surName=fullName.split(" ")[fullName.split(" ").length-1];
        String firstName = fullName.substring(0, fullName.length() - surName.length());
        List<Integer>idE=ExcelImporter.getid(inputfile.getInputStream(),0);
       int k=1;
       int cmp=1;
        Utilisateur u=person.getPersonByName(firstName,surName);
        
        
        if(enseignant.existsByIdUtilisateur(u.getIdUtilisateur())==false){
            System.out.println("l'enseignat n'existe pas");
            return false;
        }
        
        
        else{
            System.out.println("l'enseignat exist dans la base de donnes");
            
            if(module.existsByTitre(module1)==false){
                System.out.println("l element n'exist pas");
                model.addAttribute("message","le module  n'exist pas ");
            return false;
            }
            else{
                System.out.println("le module  exist ");
                
                Module ms=module.findByTitre(module1);
                long id=ms.getIdModule();
                List<Element> listE=element.getAllByModule_IdModule(id);
                
                Niveau nv=iniveau.getNiveauByIdNiveau(ms.getNiveau().getIdNiveau());
                if(nv.getAlias().equals(classe)==false){
                  System.out.println("la class n'est pas conforme avec la classe du module");
                    model.addAttribute("message","la class n'est pas conforme avec la classe du module");
                  return false;
                }
                
                else{
                    if(listE.size()!=listElemnt.size()){

                        System.out.println("le nombre des elements dans le fichier n'est pas conforme avec le nombre d'elements");
                        model.addAttribute("message","le nombre des elements dans le fichier n'est pas conforme avec le nombre d'elements");

                        return false;
                    }
                    
                    else{
                        System.out.println("le niveaux est le meme");
                        for(int i=0;i<listE.size();i++){
                            if(k==1){
                            if(element.existsByNom(listElemnt.get(i))){
                                System.out.println("l'element"+ listElemnt.get(i)+ "exsit");
                                k=1;
                            }
                            
                            else{
                                System.out.println("l'element n'existe pas");
                                k=0;
                            }}}
                            
                        if(k==0){
                                System.out.println("il ya une erreur dans les noms des elements de l'excel");
                                model.addAttribute("message","il ya une erreur dans les nom des elements de l'excel");

                                return false;
                            }
                        
                        else{
                                System.out.println("les noms des elements sont conformes");
                                
                                List<List<Double>> listNotes=new ArrayList<>();
                                for(int i=0;i<listE.size();i++){
                                    listNotes.add(ExcelImporter.getnoteelemnt(inputfile.getInputStream(),i+4));
                                }

                                for(int j=0;j<listNotes.size();j++){
                                    for(int q=0;q<idE.size();q++){
                                        System.out.println("this"+listNotes.get(j).get(q));
                                        if(cmp==1){
                                        if(listNotes.get(j).get(q)>0.0 && listNotes.get(j).get(q)<20.0){
                                            cmp=1;

                                        }
                                        else{
                                            cmp=0;
                                            System.out.println("hors norme");
                                            model.addAttribute("message","la notes dans la colone "+j+4+" est la ligne "+(q+1)+" est hors normes");
                                        }
                                        }
                                    }
                                }
                                if(cmp==0){
                                    System.out.println("hors norme");
                                    return false;
                                }else{
                                    System.out.println("bien effectue");
                                }
                            }}
                         }
                       }
                  }
        return true;
    }


    
    

    @Override
    public void Insert( MultipartFile inputfile) throws IOException{
        String module1=ExcelImporter.getModule(inputfile.getInputStream());
        List<String>listElemnt=ExcelImporter.getElements(inputfile.getInputStream(),4);
        List<Integer>idE=ExcelImporter.getid(inputfile.getInputStream(),0);

        Module ms=module.findByTitre(module1);
        long id=ms.getIdModule();
        List<Element> listE=element.getAllByModule_IdModule(id);

        Niveau nv=iniveau.getNiveauByIdNiveau(ms.getNiveau().getIdNiveau());
        int nbr=listElemnt.size();
        List<String>listv=ExcelImporter.getvalidation(inputfile.getInputStream(),nbr+5);
        List<InscriptionAnnuelle> IA1 = new ArrayList<>();
        List<List<Double>> listNotes=new ArrayList<>();
        for(int i=0;i<listE.size();i++){
            listNotes.add(ExcelImporter.getnoteelemnt(inputfile.getInputStream(),i+4));
        }
        for(int i=0;i<idE.size();i++){
            Etudiant e=null;
            InscriptionAnnuelle as=null;
            InscriptionAnnuelle as1=null;
            e=IE.findByIdUtilisateur(idE.get(i));
            if(IA.existsByEtudiant_IdUtilisateur(idE.get(i))){
                as1=IA.findByEtudiant_IdUtilisateur(idE.get(i));
                Long id2=as1.getIdInscription();
                as=new InscriptionAnnuelle(id2,2021,"",5,5,listv.get(i),"TB","",e,nv);
                IA.save(as);
                IA1.add(as);
            }else{
                as=new InscriptionAnnuelle(2021,"",5,5,listv.get(i),"TB","",e,nv);
                IA.save(as);
                IA1.add(as);
            }}
        
        List<InscriptionMatiere> IM1 = new ArrayList<>();
        List<InscriptionMatiere> IA2 = new ArrayList<>();
        String session=ExcelImporter.getSession(inputfile.getInputStream());
        System.out.println(session);
        for(int j=0;j<listElemnt.size();j++){
            Element as=null;
            for(int z=0;z<idE.size();z++) {
                if("Normal".equals(session)){
                if(IM.existsByMatiere_IdMatiere(listE.get(j).getIdMatiere()) && IM.existsByInscriptionAnnuelle_IdInscription(IA1.get(z).getIdInscription())){
                    IA2=(IM.findByInscriptionAnnuelle_IdInscription(IA1.get(z).getIdInscription()));
                    for(int p=0;p<IA2.size();p++){
                        as=element.findByNom(listElemnt.get(p));
                        Long id2=IA2.get(p).getIdInscriptionMatiere();

                        IM1.add(new InscriptionMatiere(id2,listNotes.get(p).get(z),IA2.get(p).getNoteSR(),0 , listv.get(z), IA2.get(p).getPlusInfos(), as.getCurrentCoefficient(), as, IA1.get(z)));}
                }else{

                    IM1.add(new InscriptionMatiere(0,0 ,0 , listv.get(z), "",  listE.get(j).getCurrentCoefficient(), listE.get(j), IA1.get(z)));

                }}
                if("Rattrapage".equals(session)){
                    if(IM.existsByMatiere_IdMatiere(listE.get(j).getIdMatiere()) && IM.existsByInscriptionAnnuelle_IdInscription(IA1.get(z).getIdInscription())){
                        IA2=(IM.findByInscriptionAnnuelle_IdInscription(IA1.get(z).getIdInscription()));
                        for(int p=0;p<IA2.size();p++){
                            as=element.findByNom(listElemnt.get(p));
                            Long id2=IA2.get(p).getIdInscriptionMatiere();


                            IM1.add(new InscriptionMatiere(id2,IA2.get(p).getNoteSN(), listNotes.get(p).get(z),0 , listv.get(z), IA2.get(p).getPlusInfos(), as.getCurrentCoefficient(),as, IA1.get(z)));}
                    }else{
                        IM1.add(new InscriptionMatiere(0, 0,0 , listv.get(z), "", listE.get(j).getCurrentCoefficient(), listE.get(j), IA1.get(z)));
                    }
                }
            }
            for(int u=0;u<IM1.size();u++){
                IM.save(IM1.get(u));
            }
        }
    }
}
