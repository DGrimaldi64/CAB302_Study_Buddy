package com.example.cab302_study_buddy;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProfileController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    private int userId;


    public void initializeWithUserId(int userId) {
        this.userId = userId;
        loadUserProfileData();
    }

    private void loadUserProfileData() {
        // Retrieve user's profile data from the database based on userId
        String username = DatabaseHandler.getUsernameForId(userId);
        String phone = DatabaseHandler.getPhoneForId(userId);
        String email = DatabaseHandler.getEmailForId(userId);

        // Update UI fields with retrieved data
        usernameField.setText(username);
        phoneField.setText(phone);
        emailField.setText(email);
    }

    @FXML
    private void handleSaveChanges() {
        String newUsername = usernameField.getText();
        String newPhone = phoneField.getText();
        String newEmail = emailField.getText();

        // Validate the input fields (optional)
        // ...

        // Get the current user's id
        int userId = LoginController.current_user.getId();

        // Update user's profile data in the database
        DatabaseHandler.updateUserProfile(userId, newUsername, newPhone, newEmail);

        // Close the "Edit Profile" window
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}