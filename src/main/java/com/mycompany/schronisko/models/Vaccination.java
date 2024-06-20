/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.schronisko.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import javax.persistence.*;

/**
 *Klasa obslugujaca szczepienia
 */
@Entity
@Getter
@Setter
@ToString
@Table(name="szczepienia")
public class Vaccination implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="rodzaj_szczepienia")
    private String rodzaj_szczepienia;
    @Column(name="data_pierwszego_szczepienia")
    private String data_pierwszego_szczepienia;
    @Column(name="data_ostatniego_szczepienia")
    private String data_ostatniego_szczepienia;
    @Column(name="id__zwierzaka")
    private int id__zwierzaka;
    @ManyToOne
    @JoinColumn(name="idzwierzaka",insertable = false,updatable =false, referencedColumnName = "id")
    private Animal animal;

    public Vaccination()
    {

    }
    public Vaccination(Long id,String rodzaj_szczepienia,int id__zwierzaka,String data_pierwszego_szczepienia,String data_ostatniego_szczepienia)
    {
        this.id=id;
        this.rodzaj_szczepienia=rodzaj_szczepienia;
        this.id__zwierzaka=id__zwierzaka;
        this.data_pierwszego_szczepienia=data_pierwszego_szczepienia;
        this.data_ostatniego_szczepienia=data_ostatniego_szczepienia;
    }

    public Vaccination(String rodzaj_szczepienia,int id__zwierzaka,String data_pierwszego_szczepienia,String data_ostatniego_szczepienia)
    {
        this.rodzaj_szczepienia=rodzaj_szczepienia;
        this.id__zwierzaka=id__zwierzaka;
        this.data_pierwszego_szczepienia=data_pierwszego_szczepienia;
        this.data_ostatniego_szczepienia=data_ostatniego_szczepienia;
    }

}
