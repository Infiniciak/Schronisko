/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.schronisko.App;
import com.mycompany.schronisko.respositories.AnimalRepository;
import com.mycompany.schronisko.models.Animal;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import com.mycompany.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * FXML Controller class
 *
 * @author Bartosz i Emilia
 */
public class AnimalController implements Initializable {

    @FXML
    public void goToVaccination(ActionEvent event) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/vaccinations.fxml"));
        Stage window=(Stage)toVaccination.getScene().getWindow();
        window.setScene(new Scene(root,1024,768));
        window.setFullScreen(true);
    }

    @FXML
    public void goToAdopters(ActionEvent event) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/adopters.fxml"));
        Stage window=(Stage)toAdopters.getScene().getWindow();
        window.setScene(new Scene(root,1024,768));
        window.setFullScreen(true);
    }



    @FXML
    public TextField fieldSpecies;
    @FXML
    public TextField fieldRace;
    @FXML
    public TextField fieldSex;
    @FXML
    public TextField fieldAge;
    @FXML
    public DatePicker fieldDate;
    @FXML
    public TextField fieldStatus;
    @FXML
    public TextField fieldSearch;

    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonSave;
    @FXML
    public Button buttonUpdate;
    @FXML
    public Button buttonDelete;
    @FXML
    public Button buttonClear;
    @FXML
    public Button toVaccination;
    @FXML
    public Button toAdopters;

    SessionFactory factory = HibernateUtil.getSessionFactory();
    AnimalRepository ar = new AnimalRepository(factory);
    ObservableList ols=FXCollections.observableArrayList();


    @FXML
    private void handleAddAnimalAction(ActionEvent event) {
        System.out.println("Button clicked!");
    }

    public static boolean isValidAge(String age) {
        try {
            int ageInt = Integer.parseInt(age);
            return ageInt >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static void ShowWarning(String str) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Złe dane");
        dialog.setHeaderText(str);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType przOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(przOK);
        Optional<ButtonType> wynik = dialog.showAndWait();
    }

    @FXML
    private void addAnimal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Potwierdzenie dodania");
        dialog.setHeaderText("Czy jesteś pewny?");
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonOK) {
            System.out.println("add animal");

            String[] array = {
                    fieldSpecies.getText(),
                    fieldRace.getText(),
                    fieldSex.getText(),
                    fieldAge.getText(),
                    String.valueOf(fieldDate.getValue()),
                    fieldStatus.getText(),

            };

            if(!isValidAge(fieldAge.getText())){
                ShowWarning("Źle podany wiek! " + fieldAge.getText());
                return;
            }
            if(fieldDate.getValue() == null){
                ShowWarning("Źle podana data! ");
                return;
            }


            System.out.println(Arrays.toString(array));
            Animal newAnimal = new Animal(
                    fieldSpecies.getText(),
                    fieldRace.getText(),
                    fieldSex.getText(),
                    Integer.parseInt(fieldAge.getText()),
                    String.valueOf(fieldDate.getValue()),
                    fieldStatus.getText()
            );
            System.out.println( String.valueOf(newAnimal));

            SessionFactory factory = HibernateUtil.getSessionFactory();

           AnimalRepository animalRepository = new AnimalRepository(factory);
            animalRepository.save(newAnimal);
            showAnimals();

        }

    }

    @FXML
    public void showAnimals() {

        Animal selectedAnimal = table.getSelectionModel().getSelectedItem();

        List<Animal> la=ar.getAll();
        ols.clear();
        for(Animal a : la)
        {
            ols.add(a);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSpecies.setCellValueFactory(new PropertyValueFactory<>("gatunek"));
        colRace.setCellValueFactory(new PropertyValueFactory<>("rasa"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("plec"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("wiek"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dataprzyjecia"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(ols);

        if (selectedAnimal != null) {
            table.getSelectionModel().select(selectedAnimal);
        }

        int selectedRow = table.getSelectionModel().getSelectedIndex();
        table.requestFocus();
        table.getSelectionModel().select(selectedRow);
        table.getFocusModel().focus(selectedRow);
    }

    @FXML
    public TableView<Animal> table;

    @FXML
    public TableColumn<Animal, Long> colID;

    @FXML
    public TableColumn<Animal, String> colSpecies;

    @FXML
    public TableColumn<Animal, String> colRace;

    @FXML
    public TableColumn<Animal, String> colSex;

    @FXML
    public TableColumn<Animal, Integer> colAge;

    @FXML
    public TableColumn<Animal, DatePicker> colDate;

    @FXML
    public TableColumn<Animal, String> colStatus;

    public Animal selected;

    @FXML
    public void updateAnimal() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potwierdzenie aktualizacji");
            dialog.setHeaderText("Czy jesteś pewny?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            Label label = new Label("Gatunek" + fieldSpecies.getText() + "Rasa" + fieldRace.getText() + "Plec" + fieldSex.getText() + "Wiek" + fieldAge.getText() + "Data przyjecia" + String.valueOf(fieldDate.getValue()) + "Status" + fieldStatus.getText());
            ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonOK, buttonCancel);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == buttonOK){
                if(selected != null){
                    SessionFactory factory = HibernateUtil.getSessionFactory();
                    AnimalRepository animalRepository = new AnimalRepository(factory);
                    for(Animal a : animalRepository.getAll()) {
                        System.out.println(a);
                        if (a.getId() == selected.getId()) {
                            Animal newAnimal = new Animal(
                                    (Long) selected.getId(),
                                    fieldSpecies.getText(),
                                    fieldRace.getText(),
                                    fieldSex.getText(),
                                    Integer.parseInt(fieldAge.getText()),
                                    String.valueOf(fieldDate.getValue()),
                                    fieldStatus.getText()
                            );
                            animalRepository.update(newAnimal);
                        }

                    }
                    showAnimals();
                }
            }
                showAnimals();
            buttonUpdate.setDisable(false);
            buttonDelete.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void deleteAnimal() {
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
                AnimalRepository animalRepository = new AnimalRepository(factory);
                animalRepository.delete(selected);
                showAnimals();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void clearFields() {
        fieldSpecies.setText("");
        fieldRace.setText("");
        fieldSex.setText("");
        fieldAge.setText("");
        fieldDate.setValue(LocalDate.now());
        fieldStatus.setText("");
    }

    public void filterSearch(String searchName) {
        List<Animal> allAnimals = ar.getAll();
        ObservableList<Animal> filteredAnimals = FXCollections.observableArrayList();
        for (Animal animal : allAnimals) {
            if (animal.getGatunek().toLowerCase().contains(searchName.toLowerCase()) ||
                    animal.getRasa().toLowerCase().contains(searchName.toLowerCase()) ||
                    animal.getPlec().toLowerCase().contains(searchName.toLowerCase()) ||
                    String.valueOf(animal.getWiek()).toLowerCase().contains(searchName.toLowerCase()) ||
                    animal.getDataprzyjecia().toLowerCase().contains(searchName.toLowerCase()) ||
                    animal.getStatus().toLowerCase().contains(searchName.toLowerCase())) {
                filteredAnimals.add(animal);
            }
        }

        ols.clear();
        ols.addAll(filteredAnimals);
        table.setItems(ols);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       fieldSearch.textProperty().addListener((ObservableList,oldValue,newValue)->{
            filterSearch(newValue);
        });
        showAnimals();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selected = table.getSelectionModel().getSelectedItem();
                fieldSpecies.setText(selected.getGatunek());
                fieldRace.setText(selected.getRasa());
                fieldSex.setText(selected.getPlec());
                fieldAge.setText(String.valueOf(selected.getWiek()));
                fieldDate.setValue(LocalDate.parse(selected.getDataprzyjecia()));
                fieldStatus.setText(selected.getStatus());
            }
        });
    }
}
        