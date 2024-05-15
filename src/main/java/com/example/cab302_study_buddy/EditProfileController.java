package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class EditProfileController {

    @FXML
    private TextField usernameField;

    @FXML
    private RadioButton phoneOption;

    @FXML
    private RadioButton emailOption;

    @FXML
    private TextField contactField;

    @FXML
    private void handleSave() {
        String username = usernameField.getText();
        String contactMethod = phoneOption.isSelected() ? "Phone Number" : "Email";
        String contactInfo = contactField.getText();

        // Depending on the selected contact method, handle the contact information accordingly
        if (phoneOption.isSelected()) {
            // Handle phone number logic
        } else {
            // Handle email logic
        }

        // Update user profile information in the database
        // You need to implement this logic
    }
}
