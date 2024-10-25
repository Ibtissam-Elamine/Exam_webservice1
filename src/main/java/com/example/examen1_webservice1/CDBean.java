package com.example.examen1_webservice1;

import com.example.examen1_webservice1.model.CD;
import com.example.examen1_webservice1.ejb.CDService;
import com.example.examen1_webservice1.model.Emprunt;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;

import java.util.List;

@ManagedBean
@ViewScoped
public class CDBean {
    @EJB
    private CDService cdService;

    private List<CD> cdsDisponibles;
    private List<Emprunt> cdsEmpruntes;

    private Long utilisateurId; // ID de l'utilisateur connecté

    @PostConstruct
    public void init() {
        cdsDisponibles = cdService.listerCDsDisponibles();
        cdsEmpruntes = cdService.listerCDEmpruntes(utilisateurId);  // Liste des CD empruntés
    }

    public void emprunterCD(Long cdId) {
        cdService.emprunterCD(cdId, utilisateurId);
        cdsDisponibles = cdService.listerCDsDisponibles();  // Actualisation de la liste
        cdsEmpruntes = cdService.listerCDEmpruntes(utilisateurId);  // Actualisation des emprunts
    }

    public void retournerCD(Long cdId) {
        cdService.retournerCD(cdId, utilisateurId);
        cdsDisponibles = cdService.listerCDsDisponibles();  // Actualisation de la liste
        cdsEmpruntes = cdService.listerCDEmpruntes(utilisateurId);  // Actualisation des emprunts
    }

    public List<CD> getCdsDisponibles() {
        return cdsDisponibles;
    }

    public List<Emprunt> getCdsEmpruntes() {
        return cdsEmpruntes;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}
