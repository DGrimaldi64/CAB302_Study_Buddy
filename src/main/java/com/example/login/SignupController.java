package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupController {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^\\d{10}$");

    private static Map<String, String> users = new HashMap<>();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleSignup() {
        String username = usernameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all required fields.", Color.RED);
        } else if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match.", Color.RED);
        } else if (LoginPageController.users.containsKey(username)) {
            showMessage("Username already exists.", Color.RED);
        } else if (!isValidPhoneOrEmail(phone, email)) {
            showMessage("Invalid phone number or email address.", Color.RED);
        } else {
            String identifier = phone.isEmpty() ? email : phone;
            DatabaseHandler.insertUser(username, password, identifier);
            showMessage("Sign-up successful!", Color.GREEN);
        }
    }

    private boolean isValidPhoneOrEmail(String phone, String email) {
        return (phone.isEmpty() || VALID_PHONE_NUMBER_REGEX.matcher(phone).matches())
                && (email.isEmpty() || VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches());
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setTextFill(color);
    }

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }
}