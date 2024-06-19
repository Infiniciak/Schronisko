package com.mycompany.schronisko.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
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

    @Column(name = "idzwierzaka")
    private int idzwierzaka;

    @Column(name = "nazwisko")
    private String nazwisko;

    @ManyToOne
    @JoinColumn(name = "idzwierzaka", referencedColumnName = "id",insertable = false,updatable =false)
    private Animal animal;

    public Adopter(Long id,String imie,String nazwisko,String dataadopcji,int idzwierzaka)
    {
        this.id=id;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.dataadopcji=dataadopcji;
        this.idzwierzaka=idzwierzaka;
    }

    public Adopter(String imie,String nazwisko,String dataadopcji,int idzwierzaka)
    {
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.idzwierzaka=idzwierzaka;
        this.dataadopcji=dataadopcji;
    }
}