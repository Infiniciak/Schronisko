package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Vaccination;
import java.util.List;
/**
 * Interfejs deklarujacy metody dla szczepien
 */
public interface VaccinationRepositoryInterface {
    /**
     * metoda pokazujaca wszystkie szczepienia
     */
    List<Vaccination> getAll();
    /**
     * metoda otrzymywania poprzez id
     */
    Vaccination getById(Long id);
    /**
     * metoda zapisywania szczepienia
     */
    void save(Vaccination vaccination);
    /**
     * metoda aktualizacji szczepienia
     */
    void update(Vaccination vaccination);
    /**
     * metoda usuwania szczepienia
     */
    void delete(Vaccination vaccination);
}
