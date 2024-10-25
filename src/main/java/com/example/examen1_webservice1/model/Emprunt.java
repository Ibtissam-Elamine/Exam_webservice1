package com.example.examen1_webservice1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CD cd;

    @ManyToOne
    private Utilisateur utilisateur;

    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    // Getters, Setters, Constructeurs
}
