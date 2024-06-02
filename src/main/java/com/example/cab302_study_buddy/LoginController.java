package com.example.cab302_study_buddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.cab302_study_buddy.DatabaseHandler.getIdForUsername;


/**
 * Defines the JavaFX code to display the login page, including calling Database methods
 */
public class LoginController {

    public static UserLogin current_user = new UserLogin(0, null, null, null);
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

            // Setting data for current user class
            current_user.setId(getIdForUsername(username));
            current_user.setUsername(username);
            current_user.setPassword(password);

            switchToHomePage();
        } else {
            messageLabel.setText("Invalid username or password.");
            messageLabel.setTextFill(Color.RED);
        }
    }
    @FXML
    private void switchToHomePage() throws IOException {
        Stage stage = (Stage)usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
        stage.show();

        // Close the login window
        //Stage loginStage = (Stage) usernameField.getScene().getWindow();
        //loginStage.close();
    }


    @FXML
    private void switchToSignup(ActionEvent event) throws IOException {
        Parent signupParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup-view.fxml")));
        Scene signupScene = new Scene(signupParent);
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(signupScene);
        window.show();
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getSource() == usernameField) {
                passwordField.requestFocus();
            }

            else if (event.getSource() == passwordField) {
                handleLogin();
            }
        }
    }
}
