package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class ChangePasswordController {

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleChangePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if the current password is correct
        if (!isCurrentPasswordValid(currentPassword)) {
            showAlert(Alert.AlertType.ERROR, "Incorrect Password", "The current password is incorrect.");
            return;
        }

        // Check if the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "The new password and confirm password do not match.");
            return;
        }

        // Check if the new password meets complexity requirements
        if (!isPasswordComplex(newPassword)) {
            showAlert(Alert.AlertType.ERROR, "Weak Password", "The new password does not meet complexity requirements.");
            return;
        }

        // Password change successful, update password in the database
        // Code for updating password in the database goes here

        showAlert(Alert.AlertType.INFORMATION, "Success", "Password changed successfully.");
    }

    private boolean isCurrentPasswordValid(String currentPassword) {
        // Logic to check if the current password is correct
        // You need to implement this based on how passwords are stored and verified in your system
        // For example, if you're using a database, you would compare the provided current password with the stored hashed password
        // Replace this with your actual logic
        String storedPassword = ""; // Get the stored hashed password from the database
        return currentPassword.equals(storedPassword);
    }

    private boolean isPasswordComplex(String password) {
        // Logic to check if the password meets complexity requirements
        // You can define your own rules for password complexity here
        // For example, requiring a minimum length, presence of uppercase and lowercase letters, numbers, and special characters
        // Replace this with your actual password complexity validation logic
        return password.length() >= 8 && password.matches(".*[a-z].*") && password.matches(".*[A-Z].*") && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
