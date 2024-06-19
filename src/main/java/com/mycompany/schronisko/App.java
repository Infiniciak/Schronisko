package com.mycompany.schronisko;

//import com.mysql.cj.Session;
import com.mycompany.schronisko.models.Adopter;
import com.mycompany.schronisko.respositories.AdopterRepository;
import com.mycompany.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
        import java.util.List;

//import javax.validation.Configuration;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("animals"), 1024, 768);
        stage.setTitle("Aplikacja dla schroniska");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        System.out.println("works");
        //for removing

//       Połaczenie baza=new Połaczenie();
//        baza.Polacz();
//        System.out.println("Status połączenia: " + baza.getPolaczenie());

        // Konfiguracja Hibernate
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();



       launch();
      testConnectionWithDatabase(session);




//        session.beginTransaction();
//
//        // Seeder danych
//        seedDatabase(session);
//
//        session.getTransaction().commit();
//        session.close();
//        sessionFactory.close();

// ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
// configuration.getProperties()).build();
// SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
// Session session = factory.openSession();

// }
// }

    }
    private static void testConnectionWithDatabase(Session session) {
        try {
            // Próba wykonania zapytania SQL
            session.beginTransaction();

            // Proste zapytanie SQL SELECT z tabeli (zastąp "nazwa_tabeli" właściwą nazwą tabeli)
            SQLQuery query = session.createSQLQuery("SELECT * FROM zwierzaki");

            // Opcjonalnie: Ustawienie maksymalnej liczby wyników (jeśli chcesz)
            // query.setMaxResults(10);

            // Wykonanie zapytania i pobranie wyników
            List<Object[]> results = query.list();

            // Wyświetlenie wyników
            for(Object[] row : results) {
                for(Object value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }

            session.getTransaction().commit();
            System.out.println("Query executed successfully!");
        } catch (Exception e) {
            // Obsługa błędów
            System.err.println("Error executing query: " + e.getMessage());
        } finally {
            // Zamykanie sesji
            session.close();

        }
    }

    private static void seedDatabase(Session session) {

        System.out.println("db seed in progress");

//Zwierzak zwierzak1 = new Zwierzak("Pies", "Owczarek Niemiecki","Samiec",5,"2024-01-01","Aktywny");
//Zwierzak zwierzak2 = new Zwierzak("Piess", "Owczarek Niemieckii","Samiec",3,"2024-01-01","Aktywny");
//Adoptujacy adopt1 = new Adoptujacy();
//adopt1.setImie("Jan");
//adopt1.setNazwisko("Kowalski");
//adopt1.setIdzwierzaka(3);
//adopt1.setData_adopcji("2024-01-01");
//adopt1.setZwierzak(zwierzak1);
//
//Adoptujacy adopt2 = new Adoptujacy();
//adopt2.setImie("Dawid");
//adopt2.setNazwisko("Bez");
//adopt2.setIdzwierzaka(4);
//adopt2.setData_adopcji("2024-01-02");
//adopt2.setZwierzak(zwierzak2);

        // Transaction transaction = session.beginTransaction();
// session.save(zwierzak1);
// session.save(zwierzak2);
// session.save(adopt1);
// session.save(adopt2);
// transaction.commit();
// System.out.println("Transaction Completed !");
//
// Session session = factory.openSession();
//ArrayList<Zwierzak> lista = (ArrayList<Zwierzak>)
//session.createSQLQuery("select * from ziwerzak").addEntity(Zwierzak.class).list();
// for (Zwierzak zwierzak : lista)
//System.out.println(zwierzak.toString());
//session.close();
// factory.close();
    }

}