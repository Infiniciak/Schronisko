package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Vaccination;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Repozytorium dla klasy szczepienia
 */
public class VaccinationRepository implements VaccinationRepositoryInterface {
    /**
     * Stworzenie sessionFactory dla klasy Vaccination
     */
    private final SessionFactory sessionFactory;

    /**
     * Stworzenie konstruktora klasy Vaccination
     */
    public VaccinationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Stworzenie metody pokazujacej wszystkie dane
     */
    @Override
    public List<Vaccination> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Vaccination", Vaccination.class).list();
        }
    }

    /**
     * Stworzenie metody szukajacej po id
     */
    @Override
    public Vaccination getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Vaccination.class, id);
        }
    }

    /**
     * Stworzenie metody zapisujacej dane
     */
    @Override
    public void save(Vaccination vaccination) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(vaccination);
            transaction.commit();
        }
    }

    /**
     * Stworzenie metody aktualizujacej dane
     */
    @Override
    public void update(Vaccination vaccination) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(vaccination);
            transaction.commit();
        }
    }

    /**
     * Stworzenie metody usuwajacej dane
     */
    @Override
    public void delete(Vaccination vaccination) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(vaccination);
            transaction.commit();
        }
    }
}

