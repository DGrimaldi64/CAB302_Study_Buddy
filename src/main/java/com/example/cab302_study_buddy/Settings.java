package com.example.cab302_study_buddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class Settings {

    public static boolean isDarkMode;
    @FXML
    private Button logoutButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button darkModeButton;

    @FXML
    private Button getEditProfileButton;


    @FXML
    private void initialize() {
        deleteAccountButton.setOnAction(event -> handleDeleteAccount());
        darkModeButton.setOnAction(event -> handleDarkMode());
    }

    @FXML
    private void handleDarkMode() {
        isDarkMode = !isDarkMode;

        // Load the current scene
        Stage stage = (Stage) darkModeButton.getScene().getWindow();
        Scene scene = stage.getScene();

        // Clear the existing stylesheets
        scene.getStylesheets().clear();

        // Load the default stylesheet
        String stylesPath = "/default.css";
        URL stylesUrl = getClass().getResource(stylesPath);
        if (stylesUrl != null) {
            scene.getStylesheets().add(stylesUrl.toExternalForm());
        }

        // Conditionally load the dark mode stylesheet
        if (isDarkMode) {
            String darkmodePath = "/dark-mode.css";
            URL darkmodeUrl = getClass().getResource(darkmodePath);
            if (darkmodeUrl != null) {
                scene.getStylesheets().add(darkmodeUrl.toExternalForm());
            }
        }
    }


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
        // Show a confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Delete Account");
        confirmationDialog.setHeaderText("Are you sure you want to delete your account?");
        confirmationDialog.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the user account
            int currentUserId = LoginController.current_user.getId();
            DatabaseHandler.deleteUser(currentUserId);

            // Perform any additional cleanup or navigation after deleting the account
            // For example, you can navigate back to the login screen
            navigateToLoginScreen();
        }
    }

    private void navigateToLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        // Get the current user's id
        int userId = LoginController.current_user.getId();

        // Get the controller instance and call initializeWithUserId
        EditProfileController controller = loader.getController();
        controller.initializeWithUserId(userId);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
    }
}
