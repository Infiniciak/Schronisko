package com.mycompany.schronisko.respositories;

import com.mycompany.schronisko.models.Vaccination;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VaccinationRepository implements VaccinationRepositoryInterface {

    private final SessionFactory sessionFactory;

    public VaccinationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Vaccination> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Vaccination", Vaccination.class).list();
        }
    }

    @Override
    public Vaccination getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Vaccination.class, id);
        }
    }

    @Override
    public void save(Vaccination vaccination) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(vaccination);
            transaction.commit();
        }
    }

    @Override
    public void update(Vaccination vaccination) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(vaccination);
            transaction.commit();
        }
    }

    @Override
    public void delete(Vaccination vaccination) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(vaccination);
            transaction.commit();
        }
    }
}

