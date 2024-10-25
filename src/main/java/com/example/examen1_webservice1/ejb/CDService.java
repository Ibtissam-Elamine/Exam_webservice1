package com.example.examen1_webservice1.ejb;

import com.example.examen1_webservice1.model.Utilisateur;
import com.example.examen1_webservice1.model.CD;
import com.example.examen1_webservice1.model.Emprunt;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class CDService {

    @PersistenceContext
    private EntityManager em;

    public List<CD> listerCDsDisponibles() {
        return em.createQuery("SELECT c FROM CD c WHERE c.disponibilite = :disponibilite", CD.class)
                .setParameter("disponibilite", CD.Disponibilite.DISPONIBLE)
                .getResultList();
    }

    public void emprunterCD(Long cdId, Long utilisateurId) {
        CD cd = em.find(CD.class, cdId);
        Utilisateur utilisateur = em.find(Utilisateur.class, utilisateurId);

        if (cd != null && cd.getDisponibilite() == CD.Disponibilite.DISPONIBLE) {
            cd.setDisponibilite(CD.Disponibilite.EMPRUNTE);

            Emprunt emprunt = new Emprunt();
            emprunt.setCd(cd);
            emprunt.setUtilisateur(utilisateur);
            emprunt.setDateEmprunt(LocalDate.now());

            em.persist(emprunt);
            em.merge(cd);
        }
    }

    public void retournerCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);

        if (cd != null && cd.getDisponibilite() == CD.Disponibilite.EMPRUNTE) {
            cd.setDisponibilite(CD.Disponibilite.DISPONIBLE);
            Emprunt emprunt = em.createQuery("SELECT e FROM Emprunt e WHERE e.cd.id = :cdId AND e.dateRetour IS NULL", Emprunt.class)
                    .setParameter("cdId", cdId)
                    .getSingleResult();
            emprunt.setDateRetour(LocalDate.now());
            em.merge(emprunt);
            em.merge(cd);
        }
    }
    public List<Emprunt> listerCDEmpruntes(Long utilisateurId) {
        return em.createQuery("SELECT e FROM Emprunt e WHERE e.utilisateur.id = :utilisateurId AND e.dateRetour IS NULL", Emprunt.class)
                .setParameter("utilisateurId", utilisateurId)
                .getResultList();
    }

    public void retournerCD(Long cdId, Long utilisateurId) {
        Emprunt emprunt = em.createQuery("SELECT e FROM Emprunt e WHERE e.cd.id = :cdId AND e.utilisateur.id = :utilisateurId AND e.dateRetour IS NULL", Emprunt.class)
                .setParameter("cdId", cdId)
                .setParameter("utilisateurId", utilisateurId)
                .getSingleResult();
        if (emprunt != null) {
            emprunt.setDateRetour(LocalDate.now());
            emprunt.getCd().setDisponibilite(CD.Disponibilite.DISPONIBLE);
            em.merge(emprunt);
            em.merge(emprunt.getCd());
        }
    }

}
