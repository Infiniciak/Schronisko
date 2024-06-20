package com.mycompany.schronisko.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 *Klasa obslugujaca adoptujacych
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "adoptujacy")
public class Adopter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataadopcji")
    private String dataadopcji;

    @Column(name = "imie")
    private String imie;

    @Column(name = "id_zwierzaka")
    private Integer id_zwierzaka;

    @Column(name = "nazwisko")
    private String nazwisko;

    @ManyToOne
    @JoinColumn(name = "id_zwierzaka",insertable = false,updatable =false)
    private Animal animal;

    public Adopter()
    {

    }

    public Adopter(Long id,String imie,String nazwisko,String dataadopcji,int id_zwierzaka)
    {
        this.id=id;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.dataadopcji=dataadopcji;
        this.id_zwierzaka=id_zwierzaka;
    }

    public Adopter(String imie,String nazwisko,String dataadopcji,int id_zwierzaka)
    {
        this.id=id;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.id_zwierzaka=id_zwierzaka;
        this.dataadopcji=dataadopcji;
    }


}