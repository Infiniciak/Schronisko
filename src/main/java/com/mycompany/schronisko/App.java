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
 * Aplikacja rozruchowa
 * Klasa rozszerza aplikacja JavaFX i ustala GUI and konfiguruje Hibernate
 */
public class App extends Application {
    /**
     *Stworzenie glownej scena aplikacji
     */
    private static Scene scene;

    /**
    *Uruchomienie glownej sceny
     * @param stage glowna scena dla aplikacji gdzie
     * jej scena moze byc ustawiona
     * @throws IOException w razie problemu ladowania
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menu"), 1024, 768);
        stage.setTitle("Aplikacja dla schroniska");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     Ustawienie roota do glownej sceny
     @param fxml nazwa pliku FXML
     @throws IOException w razie problemu ladowania
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     *Laduje plik FXML i zwraca jako obiekt Parent
     *@param fxml nazwa pliku FXML do zaladowania
     *@return zaladowany obiekt Parent
     *@throws IOException w razie problemu zaladowania
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     *glowna metoda aplikacji ladujaca FXML i Hibernate
    * @param args argumenty wiersza polecen
     */
    public static void main(String[] args) {
        System.out.println("works");


        /**
         *Konfiguracja hibernate
         */
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        launch();
        testConnectionWithDatabase(session);

    }


    /**
     *test polaczenia z baza poprzez zapytanie SQL
     @param session sesja Hibernate uzyta w celu wywolania komendy
     */
    private static void testConnectionWithDatabase(Session session) {
        try {
            // Proba wykonania zapytania
            session.beginTransaction();
            // Proste zapytanie SQL SELECT z tabeli
            SQLQuery query = session.createSQLQuery("SELECT * FROM zwierzaki");

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
}