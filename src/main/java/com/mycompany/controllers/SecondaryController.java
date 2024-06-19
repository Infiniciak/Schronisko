package com.mycompany.controllers;

import java.io.IOException;

import com.mycompany.schronisko.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("zwierzaki");
    }
}