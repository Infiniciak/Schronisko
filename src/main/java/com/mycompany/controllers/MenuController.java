package com.mycompany.controllers;

import com.mycompany.schronisko.models.Adopter;
import com.mycompany.schronisko.models.Vaccination;
import com.mycompany.schronisko.respositories.AdopterRepository;
import com.mycompany.schronisko.respositories.AnimalRepository;
import com.mycompany.schronisko.models.Animal;
import com.mycompany.schronisko.respositories.VaccinationRepository;
import com.mycompany.util.HibernateUtil;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.swing.text.html.ImageView;

/**
 * Klasa kontrolera dla menu
 */
public class MenuController implements Initializable {

    /**
     * Przyciski do przejscia do sceny zwierzaki
     */
    @FXML
    public Button toAnimals;
    /**
     * Przyciski do przejscia do sceny szczepienia
     */
    @FXML
    public Button toVaccination;
    /**
     * Przyciski do przejscia do sceny adoptujacy
     */
    @FXML
    public Button toAdopters;

    /**
     * Metoda przejscia do sceny szczepien
     */
    @FXML
    public void goToVaccination(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/vaccinations.fxml"));
        Stage window = (Stage) toVaccination.getScene().getWindow();
        window.setScene(new Scene(root, 1024, 768));
        window.setFullScreen(true);
    }
    /**
     * Metoda przejscia do sceny zwierzat
     */
    @FXML
    public void goToAnimals(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/animals.fxml"));
        Stage window = (Stage) toAnimals.getScene().getWindow();
        window.setScene(new Scene(root, 1024, 768));
        window.setFullScreen(true);
    }

    /**
     * Metoda przejscia do sceny adoptujacych
     */
    @FXML
    public void goToAdopters(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/adopters.fxml"));
        Stage window = (Stage) toAdopters.getScene().getWindow();
        window.setScene(new Scene(root, 1024, 768));
        window.setFullScreen(true);
    }

    /**
     * inicjalizacja
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
