package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Animal;
import java.util.List;
/**
 * Interfejs deklarujacy metody dla zwierzakow
 */
public interface AnimalRepositoryInterface {
    /**
     * metoda pokazujaca wszystkie zwierzaki
     */
    List<Animal> getAll();
    /**
     * metoda otrzymywania poprzez id
     */
    Animal getById(Long id);
    /**
     * metoda zapisywania zwierzaka
     */
    void save(Animal animal);
    /**
     * metoda aktualizowania zwierzaka
     */
    void update(Animal animal);
    /**
     * metoda usuwania zwierzaka
     */
    void delete(Animal animal);
}
