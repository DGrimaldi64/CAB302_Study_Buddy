package com.example.login;

import com.example.cab302_study_buddy.*;

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
import java.sql.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    public static Map<String, String> users = new HashMap<>();

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String storedPassword = DatabaseHandler.getPasswordForUsername(username);

        if (storedPassword != null && storedPassword.equals(password)) {
            messageLabel.setText("Login successful!");
            messageLabel.setTextFill(Color.GREEN);
            switchToHomePage();
        } else {
            messageLabel.setText("Invalid username or password.");
            messageLabel.setTextFill(Color.RED);
        }
    }
    @FXML
    private void switchToHomePage() throws IOException {
        Stage stage = (Stage)usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("com.example.cab302_study_buddy.welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        stage.setScene(scene);
        stage.show();

        // Close the login window
        Stage loginStage = (Stage) usernameField.getScene().getWindow();
        loginStage.close();
    }


    @FXML
    private void switchToSignup(ActionEvent event) throws IOException {
        Parent signupParent = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene signupScene = new Scene(signupParent);
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(signupScene);
        window.show();
    }
}