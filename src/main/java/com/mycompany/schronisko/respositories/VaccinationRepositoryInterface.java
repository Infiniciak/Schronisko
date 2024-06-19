package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Vaccination;
import java.util.List;

public interface VaccinationRepositoryInterface {
    List<Vaccination> getAll();
    Vaccination getById(Long id);
    void save(Vaccination vaccination);
    void update(Vaccination vaccination);
    void delete(Vaccination vaccination);
}
