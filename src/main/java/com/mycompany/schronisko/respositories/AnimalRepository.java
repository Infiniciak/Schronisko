package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Animal;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AnimalRepository implements AnimalRepositoryInterface {

    private final SessionFactory sessionFactory;

    public AnimalRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Animal> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Animal", Animal.class).list();
        }
    }

    @Override
    public Animal getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Animal.class, id);
        }
    }

    @Override
    public void save(Animal animal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(animal);
            transaction.commit();
        }
    }

    @Override
    public void update(Animal animal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(animal);
            transaction.commit();
        }
    }

    @Override
    public void delete(Animal animal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(animal);
            transaction.commit();
        }
    }
}

