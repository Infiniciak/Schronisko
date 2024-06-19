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
 *
 * @author Bartosz
 */
@Entity
@NoArgsConstructor
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
    @Column(name="idzwierzaka")
    private int idzwierzaka;
    @Column(name="data_pierwszego_szczepienia")
    private String data_pierwszego_szczepienia;
    @Column(name="data_ostatniego_szczepienia")
    private String data_ostatniego_szczepienia;
    @ManyToOne
    @JoinColumn(name="idzwierzaka", referencedColumnName = "id",insertable = false,updatable =false)
    private Animal animal;



    public Vaccination(Long id,String rodzaj_szczepienia,int idzwierzaka,String data_pierwszego_szczepienia,String data_ostatniego_szczepienia)
    {
        this.id=id;
        this.rodzaj_szczepienia=rodzaj_szczepienia;
        this.idzwierzaka=idzwierzaka;
        this.data_pierwszego_szczepienia=data_pierwszego_szczepienia;
        this.data_ostatniego_szczepienia=data_ostatniego_szczepienia;
    }

    public Vaccination(String rodzaj_szczepienia,int idzwierzaka,String data_pierwszego_szczepienia,String data_ostatniego_szczepienia)
    {
        this.rodzaj_szczepienia=rodzaj_szczepienia;
        this.idzwierzaka=idzwierzaka;
        this.data_pierwszego_szczepienia=data_pierwszego_szczepienia;
        this.data_ostatniego_szczepienia=data_ostatniego_szczepienia;
    }

}
