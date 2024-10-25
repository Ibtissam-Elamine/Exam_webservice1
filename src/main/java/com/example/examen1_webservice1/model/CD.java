package com.example.examen1_webservice1.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class CD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String artiste;
    private int annee;

    @Enumerated(EnumType.STRING)
    private Disponibilite disponibilite;

    // Getters, Setters, Constructeurs

    public enum Disponibilite {
        DISPONIBLE, EMPRUNTE
    }
}
