package com.mycompany.util;

import com.mycompany.schronisko.models.Animal;
import com.mycompany.schronisko.models.Adopter;
import com.mycompany.schronisko.models.Vaccination;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
/**
 *okresla dostep do obiektów sesji Hibernate i SessionFactory za pośrednictwem klas,opcji i metod JPA
 */

public class HibernateUtil {
    /**
     *zmienna dla SessionFactory
     */

    private static SessionFactory sessionFactory;
    /**
     *metoda pozwalajaca okreslic oczekiwane przez nas ustawienia, klasy itd.
     */

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/schronisko");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "123");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL82Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Adopter.class);
                configuration.addAnnotatedClass(Animal.class);
                configuration.addAnnotatedClass(Vaccination.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    /**
     *metoda zamykajaca sesje
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}
