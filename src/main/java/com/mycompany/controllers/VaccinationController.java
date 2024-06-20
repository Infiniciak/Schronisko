/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.controllers;



import com.mycompany.schronisko.App;
import com.mycompany.schronisko.models.Adopter;
import com.mycompany.schronisko.models.Vaccination;
import com.mycompany.schronisko.respositories.AdopterRepository;
import com.mycompany.schronisko.respositories.AnimalRepository;
import com.mycompany.schronisko.models.Animal;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import com.mycompany.schronisko.respositories.VaccinationRepository;
import com.mycompany.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Klasa kontrolera dla szczepien
 */
public class VaccinationController implements Initializable {


    /**
     * Funkcja umozliwiajaca przejscie do menu
     */
    @FXML
    public void goToMenu(ActionEvent event) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/menu.fxml"));
        Stage window=(Stage)toMenu.getScene().getWindow();
        window.setScene(new Scene(root,1024,768));
        window.setFullScreen(true);
    }


    /**
     * Okreslenie pol danej klasy
     */
    @FXML
    public TextField fieldVaccineType;
    @FXML
    public DatePicker fieldFirstVaccination;
    @FXML
    public DatePicker fieldLastVaccination;
    @FXML
    public TextField fieldPetID;
    @FXML
    public TextField fieldSearch;


    /**
     * Okreslenie przyciskow danej klasy
     */
    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonSave;
    @FXML
    public Button buttonUpdate;
    @FXML
    public Button buttonDelete;
    @FXML
    public Button toMenu;


    /**
     * Inicjowanie SessionFactory przy uzyciu HibernateUtil w celu stworzenia sesji
     * Stworzenie instancji klasy VaccinationRespository
     * Stworzenie listy do aktualizowania elementow interfejsu
     */

    SessionFactory factory = HibernateUtil.getSessionFactory();
    VaccinationRepository vr = new VaccinationRepository(factory);
    ObservableList ols = FXCollections.observableArrayList();

    /**
     * Funkcja isValidString sprawdza, czy dany string nie jest null i czy nie jest pusty po przycięciu białych znaków
     */
    public boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     *   Funkcja ShowWarning wyświetla modalne okno dialogowe z przekazanym komunikatem ostrzegawczym i przyciskiem OK
     */
    public void ShowWarning(String str) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Złe dane");
        dialog.setHeaderText(str);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonOK);
        Optional<ButtonType> result = dialog.showAndWait();
    }
    /**
     *  Funkcja addVaccinations wyświetla modalne okno dialogowe z prośbą o potwierdzenie dodania nowych szzczepien, a po uzyskaniu zgody użytkownika zbiera dane z pól tekstowych i zapisuje do tablicy
     */
    @FXML

    public void addVaccinations() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Potwierdzenie dodania");
        dialog.setHeaderText("Czy jesteś pewny?");
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonOK) {
            System.out.println("add vaccination");

            String[] array = {
                    fieldVaccineType.getText(),
                    String.valueOf(fieldFirstVaccination.getValue()),
                    String.valueOf(fieldLastVaccination.getValue()),
            };
            /**
             *   Sprawdzanie podanych danych
             */
            if (fieldFirstVaccination.getValue() == null) {
                ShowWarning("Źle podana data! ");
                return;
            }
            /**
             *   Sprawdzanie podanych danych
             */
            if (fieldLastVaccination.getValue() == null) {
                ShowWarning("Źle podana data! ");
                return;
            }

            System.out.println(Arrays.toString(array));
            /**
             *   Tworzenie obiektu szczepienia z podanymi danymi przez uzytkownika
             */
            Vaccination newVaccination = new Vaccination(
                    fieldVaccineType.getText(),
                    String.valueOf(fieldFirstVaccination.getValue()),
                    String.valueOf(fieldLastVaccination.getValue())
            );
            System.out.println(newVaccination);

            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build();
            SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);

            VaccinationRepository vaccinationRepository = new VaccinationRepository(factory);
            vaccinationRepository.save(newVaccination);
        }

    }


    /**
     *   Funkcja showVaccinations pobiera listę szczepien, aktualizuje tabelę nowymi danymi oraz następnie przywraca wcześniejszy wybór w tabeli
     */
    @FXML
    private void showVaccinations() {
        Vaccination selectedVaccination = table.getSelectionModel().getSelectedItem();

        List<Vaccination> lv = vr.getAll();
        ols.clear();
        for (Vaccination v : lv) {
            ols.add(v);
        }

        colPetID.setCellValueFactory(new PropertyValueFactory<>("id__zwierzaka"));
        colVaccineType.setCellValueFactory(new PropertyValueFactory<>("rodzaj_szczepienia"));
        colFirstVaccination.setCellValueFactory(new PropertyValueFactory<>("data_pierwszego_szczepienia"));
        colLastVaccination.setCellValueFactory(new PropertyValueFactory<>("data_ostatniego_szczepienia"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.setItems(ols);

        if (selectedVaccination != null) {
            table.getSelectionModel().select(selectedVaccination);
        }

        int selectedRow = table.getSelectionModel().getSelectedIndex();
        table.requestFocus();
        table.getSelectionModel().select(selectedRow);
        table.getFocusModel().focus(selectedRow);
    }

    /**
     *  Stworzenie elementow dla wyswietlenia tabeli
     */
    @FXML
    public TableView<Vaccination> table;

    @FXML
    public TableColumn<Vaccination,Long> colID;

    @FXML
    public TableColumn<Vaccination,String> colVaccineType;

    @FXML
    public TableColumn<Vaccination,DatePicker> colFirstVaccination;

    @FXML
    public TableColumn<Vaccination,DatePicker> colLastVaccination;
    @FXML
    public TableColumn<Vaccination,String> colPetID;

    /**
     *  Stworzenie elementu do wybrania elementu
     */
    public Vaccination selected;


    /**
     * Funkcja updateVaccinations wyświetla modalne okno dialogowe z prośbą o potwierdzenie aktualizacji danych szczepien, a po uzyskaniu zgody aktualizuje dane wybrane szczepienie w bazie, po czym odświeża widok tabeli i ponownie włącza przyciski AKTUALIZUJ i USUŃ
     */
    @FXML public void updateVaccinations() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potwierdzenie aktualizacji");
            dialog.setHeaderText("Czy jesteś pewny?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            Label label = new Label("Rodzaj szczepienia" + fieldVaccineType.getText() + "Data pierwszego szczepienia" + String.valueOf(fieldFirstVaccination.getValue()) + "Data ostatniego szczepienia" + String.valueOf(fieldLastVaccination.getValue()));
            ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == buttonOK) {
                if (selected != null) {
                    SessionFactory factory = HibernateUtil.getSessionFactory();
                    VaccinationRepository vaccinationRepository = new VaccinationRepository(factory);
                    for (Vaccination v : vaccinationRepository.getAll()) {
                        System.out.println(v);
                        if (v.getId() == selected.getId()) {
                            Vaccination newVaccination = new Vaccination(
                                    (Long) selected.getId(),
                                    fieldVaccineType.getText(),
                                    Integer.parseInt(fieldPetID.getText()),
                                    String.valueOf(fieldFirstVaccination.getValue()),
                                    String.valueOf(fieldLastVaccination.getValue()));
                            vaccinationRepository.update(newVaccination);
                        }

                    }
                    showVaccinations();
                }
        }
        showVaccinations();
        buttonUpdate.setDisable(false);
        buttonDelete.setDisable(false);
    } catch(Exception e){
        e.printStackTrace();
            }
    }


    /**
     *Funkcja deleteVaccinations wyświetla modalne okno dialogowe z prośbą o potwierdzenie usunięcia szczepienia, a po uzyskaniu zgody usuwa wybrane szczepienie z bazy danych za pomocą VaccinationRepository i odświeża widok tabeli
     */
    @FXML
    public void deleteVaccinations() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potwierdzenie usuniecia");
            dialog.setHeaderText("Czy jesteś pewny?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == buttonOK) {
                SessionFactory factory = HibernateUtil.getSessionFactory();
                VaccinationRepository vaccinationRepository = new VaccinationRepository(factory);
                vaccinationRepository.delete(selected);
                showVaccinations();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *Funkcja clearFields czyści dane podane w formularzu ustawiając je na puste ciągi znaków a date ustawia na aktualną
     */
    @FXML
    public void clearFields() {
        fieldPetID.setText("");
        fieldVaccineType.setText("");
        fieldFirstVaccination.setValue(LocalDate.now());
        fieldLastVaccination.setValue(LocalDate.now());
    }

    /**
     *Funkcja filterSearch filtruje listę wszystkie szczepienia na podstawie przekazanego ciągu znaków i wyświetla tylko te szczepienia które posiadają wpisywany tekst
     */
    public void filterSearch(String searchName) {
        List<Vaccination> allVaccinations = vr.getAll();
        ObservableList<Vaccination> filteredVaccinations = FXCollections.observableArrayList();
        for (Vaccination vaccination : allVaccinations) {
            if (vaccination.getRodzaj_szczepienia().toLowerCase().contains(searchName.toLowerCase()) ||
                    vaccination.getData_pierwszego_szczepienia().toLowerCase().contains(searchName.toLowerCase()) ||
                    vaccination.getData_ostatniego_szczepienia().toLowerCase().contains(searchName.toLowerCase())) {
                filteredVaccinations.add(vaccination);
            }
        }

        ols.clear();
        ols.addAll(filteredVaccinations);
        table.setItems(ols);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fieldSearch.textProperty().addListener((ObservableList,oldValue,newValue)->{
            filterSearch(newValue);
        });
        showVaccinations();
        /**
         *Kod dodaje nasłuchiwacz do tabeli, który aktualizuje pola tekstowe oraz zmienna "selected" danymi wybranego wiersza, gdy użytkownik nacisinie jakis wiersz
         */        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selected = table.getSelectionModel().getSelectedItem();
                fieldFirstVaccination.setValue(LocalDate.parse(selected.getData_pierwszego_szczepienia()));
                fieldLastVaccination.setValue(LocalDate.parse(selected.getData_ostatniego_szczepienia()));
                fieldVaccineType.setText(selected.getRodzaj_szczepienia());
            }
        });
    }
}

        