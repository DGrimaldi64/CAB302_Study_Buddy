package com.example.cab302_study_buddy;

import com.example.authentication.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StudyBuddyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseHandler.createTable();
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        primaryStage.setTitle("Study Buddy Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}