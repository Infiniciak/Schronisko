/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.schronisko.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *Klasa obslugujaca zwierzaki
 */
@Entity
@Getter
@Setter
@Table(name = "zwierzaki")
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gatunek")
    private String gatunek;

    @Column(name = "rasa")
    private String rasa;

    @Column(name = "plec")
    private String plec;

    @Column(name = "wiek")
    private int wiek;

    @Column(name = "dataprzyjecia")
    private String dataprzyjecia;

    @Column(name = "status")
    private String status;


    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "animal")
    private List<Adopter> adopter;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "animal")
    private List<Vaccination> vaccination;

public Animal()
{

}
    public Animal(String gatunek, String rasa, String plec, int wiek, String dataprzyjecia, String status) {
        this.gatunek = gatunek;
        this.rasa = rasa;
        this.plec = plec;
        this.wiek = wiek;
        this.dataprzyjecia = dataprzyjecia;
        this.status = status;
    }

    public Animal(Long id, String gatunek, String rasa, String plec, int wiek, String dataprzyjecia, String status) {
        this.id = id;
        this.gatunek = gatunek;
        this.rasa = rasa;
        this.plec = plec;
        this.wiek = wiek;
        this.dataprzyjecia = dataprzyjecia;
        this.status = status;
    }
}
