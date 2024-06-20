package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Adopter;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
/**
 * Repozytorium dla klasy Adopter
 */
public class AdopterRepository implements AdopterRepositoryInterface {

    /**
     * Stworzenie sessionFactory dla klasy Adapter
     */
    private final SessionFactory sessionFactory;

    /**
     * Stworzenie konstruktora klasy Adopter
     */
    public AdopterRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Stworzenie metody pokazujacej wszystkie dane
     */
    @Override
    public List<Adopter> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Adopter", Adopter.class).list();
        }
    }

    /**
     * Stworzenie metody szukajacej po id
     */
    @Override
    public Adopter getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Adopter.class, id);
        }
    }

    /**
     * Stworzenie metody zapisujacej dane
     */
    @Override
    public void save(Adopter adopter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(adopter);
            transaction.commit();
        }
    }

    /**
     * Stworzenie metody aktualizujacej dane
     */
    @Override
    public void update(Adopter adopter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(adopter);
            transaction.commit();
        }
    }

    /**
     * Stworzenie metody usuwajacej dane
     */
    @Override
    public void delete(Adopter adopter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(adopter);
            transaction.commit();
        }
    }
}

