package com.example.cab302_study_buddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AccessibilityController {




    @FXML
    private void setSmallText(ActionEvent event) {

    }

    @FXML
    private void setMediumText(ActionEvent event) {

    }

    @FXML
    private void setLargeText(ActionEvent event) {

    }

    @FXML
    private void setNormal(ActionEvent event) {
        // Apply deuteranopia color filter
    }

    @FXML
    private void setDeuteranopia(ActionEvent event) {
        // Apply deuteranopia color filter
    }

    @FXML
    private void setProtanopia(ActionEvent event) {
        // Apply protanopia color filter
    }

    @FXML
    private void setTritanopia(ActionEvent event) {
        // Apply tritanopia color filter
    }

    @FXML
    protected void onBackClick(ActionEvent event) throws IOException {
        // change scene to Home
        Parent loginParent = FXMLLoader.load(getClass().getResource("home-view.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }


}