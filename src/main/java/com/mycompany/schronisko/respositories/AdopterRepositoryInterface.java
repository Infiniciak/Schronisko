package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Adopter;
import java.util.List;
/**
 * Interfejs deklarujacy metody dla adoptujacych
 */
public interface AdopterRepositoryInterface {
    /**
     * metoda pokazujaca wszystkich adoptujacych
     */
    List<Adopter> getAll();
    /**
     * metoda otrzymywania poprzez id
     */
    Adopter getById(Long id);
    /**
     * metoda zapisywania adoptujacego
     */
    void save(Adopter adopter);
    /**
     * metoda aktualizowania adoptujacego
     */
    void update(Adopter adopter);
    /**
            * metoda usuwania adoptujacego
     */
    void delete(Adopter adopter);
}
