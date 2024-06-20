package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Animal;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

/**
 * Repozytorium dla klasy Animal
 */
public class AnimalRepository implements AnimalRepositoryInterface {

    /**
     * Stworzenie sessionFactory dla klasy Animal
     */
    private final SessionFactory sessionFactory;

    /**
     * Stworzenie konstruktora klasy Adopter
     */
    public AnimalRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Stworzenie metody pokazujacej wszystkie dane
     */
    @Override
    public List<Animal> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Animal", Animal.class).list();
        }
    }

    /**
     * Stworzenie metody szukajacej po id
     */
    @Override
    public Animal getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Animal.class, id);
        }
    }

    /**
     * Stworzenie metody zapisujacej dane
     */
    @Override
    public void save(Animal animal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(animal);
            transaction.commit();
        }
    }

    /**
     * Stworzenie metody aktualizujacej dane
     */
    @Override
    public void update(Animal animal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(animal);
            transaction.commit();
        }
    }

    /**
     * Stworzenie metody usuwajacej dane
     */
    @Override
    public void delete(Animal animal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(animal);
            transaction.commit();
        }
    }
}

