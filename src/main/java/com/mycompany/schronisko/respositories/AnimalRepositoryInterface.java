package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Animal;
import java.util.List;

public interface AnimalRepositoryInterface {
    List<Animal> getAll();
    Animal getById(Long id);
    void save(Animal animal);
    void update(Animal animal);
    void delete(Animal animal);
}
