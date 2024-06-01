package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;

/**
 * Class which handles logic for updating password of a given user
 */

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

        // Implement logic to change the password
        // Verify current password, validate new password, and update password in the database

        // For demonstration, show a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Password Change");
        alert.setHeaderText(null);
        alert.setContentText("Password changed successfully!");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Optionally, you can reset the password fields after successful password change
                currentPasswordField.clear();
                newPasswordField.clear();
                confirmPasswordField.clear();
            }
        });
    }
}
