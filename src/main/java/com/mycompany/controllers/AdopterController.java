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
 * FXML Controller class
 *
 * @author Bartosz
 */
public class AdopterController implements Initializable {
    @FXML
    public void goToVaccination(ActionEvent event) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/vaccinations.fxml"));
        Stage window=(Stage)toVaccination.getScene().getWindow();
        window.setScene(new Scene(root,1024,768));
        window.setFullScreen(true);
    }

    @FXML
    public void goToAnimals(ActionEvent event) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/com/mycompany/schronisko/adopters.fxml"));
        Stage window=(Stage)toAnimals.getScene().getWindow();
        window.setScene(new Scene(root,1024,768));
        window.setFullScreen(true);
    }

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
    public Button toAnimals;


    SessionFactory factory = HibernateUtil.getSessionFactory();
    AdopterRepository ar = new AdopterRepository(factory);
    ObservableList ols = FXCollections.observableArrayList();

    @FXML
    private void handleAddAnimalAction(ActionEvent event) {
        System.out.println("Button clicked!");
    }


    public boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public void ShowWarning(String str) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Złe dane");
        dialog.setHeaderText(str);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType buttonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonOK);
        Optional<ButtonType> result = dialog.showAndWait();
    }

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

            if (fieldDate.getValue() == null) {
                ShowWarning("Źle podana data! ");
                return;
            }

            System.out.println(Arrays.toString(array));
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
        colPetID.setCellValueFactory(new PropertyValueFactory<>("idzwierzaka"));
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

    public Adopter selected;


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

    @FXML
    public void clearFields() {
        fieldName.setText("");
        fieldSurname.setText("");
        fieldPetID.setText("");
        fieldDate.setValue(LocalDate.now());
    }


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
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selected = table.getSelectionModel().getSelectedItem();
                    fieldDate.setValue(LocalDate.parse(selected.getDataadopcji()));
                    fieldPetID.setText(String.valueOf(selected.getIdzwierzaka()));
                    fieldName.setText(selected.getImie());
                    fieldSurname.setText(selected.getNazwisko());
                }
            });


        }
    }
        