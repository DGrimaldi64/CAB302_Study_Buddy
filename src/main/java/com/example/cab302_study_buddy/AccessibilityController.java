package com.example.cab302_study_buddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class AccessibilityController {

    @FXML
    private void setSmallText(ActionEvent event) {
        applyTextSize(event, "small-text");
    }

    @FXML
    private void setMediumText(ActionEvent event) {
        applyTextSize(event, "medium-text");
    }

    @FXML
    private void setLargeText(ActionEvent event) {
        applyTextSize(event, "large-text");
    }

    @FXML
    private void setNormal(ActionEvent event) {
        applyColorBlindnessEffect(event, null);
    }

    @FXML
    private void setDeuteranopia(ActionEvent event) {
        applyColorBlindnessEffect(event, createDeuteranopiaEffect());
    }

    @FXML
    private void setProtanopia(ActionEvent event) {
        applyColorBlindnessEffect(event, createProtanopiaEffect());
    }

    @FXML
    private void setTritanopia(ActionEvent event) {
        applyColorBlindnessEffect(event, createTritanopiaEffect());
    }

    @FXML
    protected void onBackClick(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getResource("home-view.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
       // StudyBuddyApplication.applyStyles(homeScene); // Ensure styles are applied on scene change
        window.setScene(homeScene);
        window.show();
    }

    private void applyTextSize(ActionEvent event, String styleClass) {
        Scene scene = ((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).getScene();
        scene.getRoot().getStyleClass().removeAll("small-text", "medium-text", "large-text");
        scene.getRoot().getStyleClass().add(styleClass);
    }

    private void applyColorBlindnessEffect(ActionEvent event, Effect effect) {
        Scene scene = ((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).getScene();
        ((Region) scene.getRoot()).setEffect(effect);
    }

    private ColorAdjust createDeuteranopiaEffect() {
        // Adjust these values to simulate Deuteranopia
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.5);
        return colorAdjust;
    }

    private ColorAdjust createProtanopiaEffect() {
        // Adjust these values to simulate Protanopia
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.8);
        return colorAdjust;
    }

    private ColorAdjust createTritanopiaEffect() {
        // Adjust these values to simulate Tritanopia
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.5);
        return colorAdjust;
    }
}
