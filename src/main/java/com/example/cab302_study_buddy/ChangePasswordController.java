package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;

public class ChangePasswordController {

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    // Method to handle changing the password
    @FXML
    private void handleChangePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.equals(confirmPassword)) {
            String username = "currentLoggedInUsername"; // Retrieve the current logged-in username from your session/context
            System.out.println("Attempting to change password for user: " + username);
            boolean success = DatabaseHandler.updatePassword(username, currentPassword, newPassword);

            if (success) {
                showAlert("Password changed successfully!", Alert.AlertType.CONFIRMATION);
                clearFields();
            } else {
                showAlert("Error changing password. Please check your current password and try again.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("New passwords do not match. Please try again.", Alert.AlertType.WARNING);
        }
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Password Change");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
