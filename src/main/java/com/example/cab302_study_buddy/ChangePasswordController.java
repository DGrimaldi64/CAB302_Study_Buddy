package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private void handleChangePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        // Perform necessary validations
        if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            showAlert("Please enter the new password and confirm it.", Alert.AlertType.ERROR);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            showAlert("New password and confirmation do not match.", Alert.AlertType.ERROR);
            return;
        }

        // Get the current user's username
        String username = LoginController.current_user.getUsername();

        // Update the password in the database
        boolean isPasswordUpdated = DatabaseHandler.updatePassword(username, currentPassword, newPassword);

        if (isPasswordUpdated) {
            showAlert("Password changed successfully!", Alert.AlertType.INFORMATION);
            closeChangePasswordWindow();
        } else {
            showAlert("Failed to change password. Please check the current password.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeChangePasswordWindow() {
        Stage stage = (Stage) currentPasswordField.getScene().getWindow();
        stage.close();
    }
}