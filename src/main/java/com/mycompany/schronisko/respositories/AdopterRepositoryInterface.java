package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Adopter;
import java.util.List;

public interface AdopterRepositoryInterface {
    List<Adopter> getAll();
    Adopter getById(Long id);
    void save(Adopter adopter);
    void update(Adopter adopter);
    void delete(Adopter adopter);
}
