package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Adopter;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdopterRepository implements AdopterRepositoryInterface {

    private final SessionFactory sessionFactory;

    public AdopterRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Adopter> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Adopter", Adopter.class).list();
        }
    }

    @Override
    public Adopter getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Adopter.class, id);
        }
    }

    @Override
    public void save(Adopter adopter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(adopter);
            transaction.commit();
        }
    }

    @Override
    public void update(Adopter adopter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(adopter);
            transaction.commit();
        }
    }

    @Override
    public void delete(Adopter adopter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(adopter);
            transaction.commit();
        }
    }
}

