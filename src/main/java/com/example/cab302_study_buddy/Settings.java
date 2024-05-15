package com.example.cab302_study_buddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings {

    @FXML
    private Button logoutButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button darkModeButton;

    @FXML
    private Button uiScaleButton;

    @FXML
    private Button colorBlindButton;

    @FXML
    private Button notificationsButton;

    @FXML
    private Button getEditProfileButton;
    // Add event handling methods here
    // For example:


    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        // Load the login scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent loginParent = loader.load();
        Scene loginScene = new Scene(loginParent);

        // Get the stage from the event source
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Set the login scene and show the stage
        stage.setScene(loginScene);
        stage.show();
    }

    @FXML
    private void handleDeleteAccount() {
        // Handle delete account logic here
    }

    @FXML
    private void handleDarkMode() {
        // Handle dark mode toggle logic here
    }

    @FXML
    private void handleUIScale() {
        // Handle UI scale adjustment logic here
    }

    @FXML
    private void handleColorBlindSettings() {
        // Handle color-blind settings logic here
    }

    @FXML
    private void handleNotifications() {
        // Handle notifications toggle logic here
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("change-password.fxml"));
        Parent changePasswordParent = loader.load();
        Scene changePasswordScene = new Scene(changePasswordParent);

        Stage changePasswordStage = new Stage();
        changePasswordStage.setTitle("Change Password");
        changePasswordStage.setScene(changePasswordScene);
        changePasswordStage.initModality(Modality.APPLICATION_MODAL);
        changePasswordStage.showAndWait();
    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-profile-view.fxml"));
        Parent editProfileParent = loader.load();
        Scene editProfileScene = new Scene(editProfileParent);

        Stage stage = new Stage();
        stage.setTitle("Edit Profile");
        stage.setScene(editProfileScene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}

