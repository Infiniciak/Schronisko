package com.mycompany.schronisko.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 *Klasa obslugujaca adoptujacych
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "adoptujacy",schema="public")
public class Adopter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "dataadopcji")
    private String dataadopcji;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_zwierzaka",nullable=false)
    private Animal animal;


    public Adopter(Long id,String imie,String nazwisko,String dataadopcji)
    {
        this.id=id;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.dataadopcji=dataadopcji;
    }

    public Adopter(String imie,String nazwisko,String dataadopcji)
    {
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.dataadopcji=dataadopcji;
    }

}