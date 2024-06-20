/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.controllers;



import com.mycompany.schronisko.models.Adopter;
import com.mycompany.schronisko.models.Vaccination;
import com.mycompany.schronisko.respositories.AdopterRepository;
import com.mycompany.schronisko.respositories.AnimalRepository;
import com.mycompany.schronisko.models.Animal;
import com.mycompany.schronisko.respositories.VaccinationRepository;
import com.mycompany.util.HibernateUtil;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 * Klasa kontrolera dla adoptujacych
 */
public class AdopterController implements Initializable {
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
    public TextField fieldName;
    @FXML
    public TextField fieldSurname;
    @FXML
    public TextField fieldPetID;
    @FXML
    public DatePicker fieldDate;
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
    public Button toVaccination;
    @FXML
    public Button toMenu;

    /**
     * Inicjowanie SessionFactory przy uzyciu HibernateUtil w celu stworzenia sesji
     * Stworzenie instancji klasy AdopterRespository
     * Stworzenie listy do aktualizowania elementow interfejsu
     */
    SessionFactory factory = HibernateUtil.getSessionFactory();
    AdopterRepository ar = new AdopterRepository(factory);
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
     *   Funkcja addAdopter wyświetla modalne okno dialogowe z prośbą o potwierdzenie dodania nowego adoptujacego, a po uzyskaniu zgody użytkownika zbiera dane z pól tekstowych i zapisuje do tablicy
     */
    @FXML
    private void addAdopter() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Potwierdzenie dodania");
        dialog.setHeaderText("Czy jesteś pewny?");
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonOK) {
            System.out.println("add adopter");

            String[] array = {
                    fieldName.getText(),
                    fieldSurname.getText(),
                    fieldPetID.getText(),
                    String.valueOf(fieldDate.getValue()),
            };
            /**
             *   Sprawdzanie podanych danych
             */
            if (fieldDate.getValue() == null) {
                ShowWarning("Źle podana data! ");
                return;
            }

            System.out.println(Arrays.toString(array));
            /**
             *   Tworzenie obiektu adoptujacy z podanymi danymi przez uzytkownika
             */
            Adopter newAdopter = new Adopter(
                    fieldName.getText(),
                    fieldSurname.getText(),
                    String.valueOf(fieldDate.getValue()),
                    Integer.parseInt(fieldPetID.getText())
            );
            System.out.println(String.valueOf(newAdopter));

            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build();
            SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);

            AdopterRepository adopterRespository = new AdopterRepository(factory);


            adopterRespository.save(newAdopter);

        }

    }
    /**
     *  //Funkcja showAdopters pobiera listę adoptujących, aktualizuje tabelę nowymi danymi oraz następnie przywraca wcześniejszy wybór w tabeli
     */
    @FXML
    private void showAdopters() {
        Adopter selectedAdopter = table.getSelectionModel().getSelectedItem();

        List<Adopter> la = ar.getAll();
        ols.clear();
        for (Adopter a : la) {
            ols.add(a);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("imie"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        colPetID.setCellValueFactory(new PropertyValueFactory<>("id_zwierzaka"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dataadopcji"));
        table.setItems(ols);

        if (selectedAdopter != null) {
            table.getSelectionModel().select(selectedAdopter);
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
    public TableView<Adopter> table;

    @FXML
    public TableColumn<Adopter, Long> colID;

    @FXML
    public TableColumn<Adopter, String> colName;

    @FXML
    public TableColumn<Adopter, String> colSurname;

    @FXML
    public TableColumn<Adopter, Integer> colPetID;

    @FXML
    public TableColumn<Animal, DatePicker> colDate;

    /**
     *  Stworzenie elementu do wybrania elementu
     */
    public Adopter selected;

    /**
     * Funkcja updateAdopter wyświetla modalne okno dialogowe z prośbą o potwierdzenie aktualizacji danych zwierzęcia, a po uzyskaniu zgody aktualizuje dane wybranego adoptującego w bazie, po czym odświeża widok tabeli i ponownie włącza przyciski AKTUALIZUJ i USUŃ
     */
    @FXML
    public void updateAdopter() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potwierdzenie aktualizacji");
            dialog.setHeaderText("Czy jesteś pewny?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            Label label = new Label("Imie" + fieldName.getText() + "Nazwisko" + fieldSurname.getText() + "ID zwierzaka" + fieldPetID.getText() + "Data adopcji" + String.valueOf(fieldDate.getValue()));
            ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == buttonOK) {
                if (selected != null) {
                    SessionFactory factory = HibernateUtil.getSessionFactory();
                    AdopterRepository adopterRepository = new AdopterRepository(factory);
                    for (Adopter a : adopterRepository.getAll()) {
                        System.out.println(a);
                        if (a.getId() == selected.getId()) {
                            Adopter newAdopter = new Adopter(
                                    (Long) selected.getId(),
                                    fieldName.getText(),
                                    fieldSurname.getText(),
                                    String.valueOf(fieldDate.getValue()),
                                    Integer.parseInt(fieldPetID.getText()));
                            adopterRepository.update(newAdopter);
                        }

                    }
                    showAdopters();
                }
            }
                showAdopters();
                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);
            } catch(Exception e){
                e.printStackTrace();
            }
        }




    /**
     *Funkcja deleteAdopter wyświetla modalne okno dialogowe z prośbą o potwierdzenie usunięcia adoptującego, a po uzyskaniu zgody usuwa wybraną osobę z bazy danych za pomocą AdopterRepository i odświeża widok tabeli
     */
    @FXML
    public void deleteAdopter() {
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
                AdopterRepository adopterRepository = new AdopterRepository(factory);
                adopterRepository.delete(selected);
                showAdopters();
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
        fieldName.setText("");
        fieldSurname.setText("");
        fieldPetID.setText("");
        fieldDate.setValue(LocalDate.now());
    }

    /**
     *Funkcja filterSearch filtruje listę wszystkich adoptujących na podstawie przekazanego ciągu znaków i wyświetla tylko te osoby które posiadają wpisywany tekst
     */
    public void filterSearch(String searchName) {
        List<Adopter> allAdopters = ar.getAll();
        ObservableList<Adopter> filteredAdopters = FXCollections.observableArrayList();
        for (Adopter adopter : allAdopters) {
            if (adopter.getImie().toLowerCase().contains(searchName.toLowerCase()) ||
                    adopter.getNazwisko().toLowerCase().contains(searchName.toLowerCase()) ||
                            adopter.getDataadopcji().toLowerCase().contains(searchName.toLowerCase())) {
                filteredAdopters.add(adopter);
            }
        }

        ols.clear();
        ols.addAll(filteredAdopters);
        table.setItems(ols);
    }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            fieldSearch.textProperty().addListener((ObservableList,oldValue,newValue)->{
                filterSearch(newValue);
            });
            showAdopters();
            /**
             *Kod dodaje nasłuchiwacz do tabeli, który aktualizuje pola tekstowe oraz zmienna "selected" danymi wybranego wiersza, gdy użytkownik nacisinie jakis wiersz
             */            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selected = table.getSelectionModel().getSelectedItem();
                    fieldDate.setValue(LocalDate.parse(selected.getDataadopcji()));
                    fieldPetID.setText(String.valueOf(selected.getId_zwierzaka()));
                    fieldName.setText(selected.getImie());
                    fieldSurname.setText(selected.getNazwisko());
                }
            });


        }
    }
        