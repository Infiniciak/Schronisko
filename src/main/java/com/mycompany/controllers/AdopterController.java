/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.controllers;



import com.mycompany.schronisko.models.Adopter;
import com.mycompany.schronisko.models.Vaccination;
import com.mycompany.schronisko.respositories.AdopterRepository;
import com.mycompany.schronisko.models.Animal;
import com.mycompany.schronisko.respositories.AnimalRepository;
import com.mycompany.util.HibernateUtil;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.beans.property.SimpleObjectProperty;
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
     * Stworzenie pola dla imienia
     */
    @FXML
    public TextField fieldName;
    /**
     * Stworzenie pola dla nazwiska
     */
    @FXML
    public TextField fieldSurname;
    /**
     * Stworzenie pola dla ID zwierzaka
     */
    @FXML
    public TextField fieldPetID;
    /**
     * Stworzenie pola dla daty adopcjki
     */
    @FXML
    public DatePicker fieldDate;
    /**
     * Stworzenie pola dla pola wyszukiwania
     */
    @FXML
    public TextField fieldSearch;

    /**
     * Okreslenie przycisku dodania
     */
    @FXML
    public Button buttonNew;
    /**
     * Okreslenie przycisku zapisu
     */
    @FXML
    public Button buttonSave;
    /**
     * Okreslenie przycisku aktualizacji
     */
    @FXML
    public Button buttonUpdate;
    /**
     * Okreslenie przycisku usuwania
     */
    @FXML
    public Button buttonDelete;
    /**
     * Okreslenie przycisku powrotu do menu
     */
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
            AnimalRepository animalRepository = new AnimalRepository(factory);
            String[] array = {
                    String.valueOf(animalRepository.getById(Long.parseLong(fieldPetID.getText())).getId()),
                    String.valueOf(fieldDate.getValue()),
                    fieldName.getText(),
                    fieldSurname.getText()
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
            Animal animal = animalRepository.getById(Long.parseLong(fieldPetID.getText()));
            Adopter newAdopter = null;
            if (animal != null) {
                                newAdopter = new Adopter(animal.getId(),fieldName.getText(),fieldSurname.getText()
                                        ,String.valueOf(fieldDate.getValue()));

                newAdopter.setAnimal(animal);
            } else {
            }
            System.out.println(newAdopter);
            AdopterRepository adopterRepository = new AdopterRepository(factory);
            adopterRepository.save(newAdopter);

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
        colSurname.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        colPetID.setCellValueFactory(cellData -> {
            Long petId = cellData.getValue().getAnimal().getId();
            return new SimpleObjectProperty<>(petId);
        });
        colDate.setCellValueFactory(new PropertyValueFactory<>("dataadopcji"));
        colName.setCellValueFactory(new PropertyValueFactory<>("imie"));


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
     *  Stworzenie elementu do wyswietlenia tabeli
     */
    @FXML
    public TableView<Adopter> table;
    /**
     *  Stworzenie kolumny dla ID
     */
    @FXML
    public TableColumn<Adopter, Long> colID;
    /**
     *  Stworzenie kolumny dla imienia
     */
    @FXML
    public TableColumn<Adopter, String> colName;
    /**
     *  Stworzenie kolumny dla nazwiska
     */
    @FXML
    public TableColumn<Adopter, String> colSurname;
    /**
     *  Stworzenie kolumny dla ID zwierzaka
     */
    @FXML
    public TableColumn<Adopter, Long> colPetID;
    /**
     *  Stworzenie kolumny dla daty adopcji
     */
    @FXML
    public TableColumn<Adopter, DatePicker> colDate;

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
            AnimalRepository animalRepository = new AnimalRepository(factory);
            Animal animal = animalRepository.getById(Long.parseLong(fieldPetID.getText()));
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
                            Adopter newAdopter = null;
                            if (animal != null) {
                                newAdopter = new Adopter(animal.getId(),fieldName.getText(),fieldSurname.getText()
                                        ,String.valueOf(fieldDate.getValue()));
                                newAdopter.setAnimal(animal);
                            } else {
                            }
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
            if (adopter.getDataadopcji().toLowerCase().contains(searchName.toLowerCase()) ||
                    adopter.getImie().toLowerCase().contains(searchName.toLowerCase()) ||
                            adopter.getNazwisko().toLowerCase().contains(searchName.toLowerCase())) {
                filteredAdopters.add(adopter);
            }
        }
        ols.clear();
        ols.addAll(filteredAdopters);
        table.setItems(ols);
    }


    /**
     *  inicjalizacja
     */
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
                    fieldName.setText(selected.getImie());
                    fieldSurname.setText(selected.getNazwisko());
                }
            });
        }
    }
        