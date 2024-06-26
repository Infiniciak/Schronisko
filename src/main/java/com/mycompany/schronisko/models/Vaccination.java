
package com.mycompany.schronisko.models;

import lombok.*;


import java.io.Serializable;
import javax.persistence.*;

/**
 *Klasa obslugujaca szczepienia
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="szczepienia",schema="public")
public class Vaccination implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="rodzaj_szczepienia")
    private String rodzaj_szczepienia;
    @Column(name="data_pierwszego_szczepienia")
    private String data_pierwszego_szczepienia;
    @Column(name="data_ostatniego_szczepienia")
    private String data_ostatniego_szczepienia;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="zwierzak_id",nullable=false)
    private Animal animal;


    public Vaccination(Long id,String rodzaj_szczepienia,String data_pierwszego_szczepienia,String data_ostatniego_szczepienia)
    {
        this.id=id;
        this.rodzaj_szczepienia=rodzaj_szczepienia;
        this.data_pierwszego_szczepienia=data_pierwszego_szczepienia;
        this.data_ostatniego_szczepienia=data_ostatniego_szczepienia;
    }

    public Vaccination(String rodzaj_szczepienia,String data_pierwszego_szczepienia,String data_ostatniego_szczepienia)
    {
        this.rodzaj_szczepienia=rodzaj_szczepienia;
        this.data_pierwszego_szczepienia=data_pierwszego_szczepienia;
        this.data_ostatniego_szczepienia=data_ostatniego_szczepienia;
    }


}
