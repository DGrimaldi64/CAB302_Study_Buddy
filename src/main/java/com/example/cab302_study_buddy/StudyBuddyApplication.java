package com.example.cab302_study_buddy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudyBuddyApplication extends Application {

    private static boolean isDarkMode = false;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(StudyBuddyApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(StudyBuddyApplication.class.getResource("default.css").toExternalForm());

        if (Settings.isDarkMode) {
            scene.getStylesheets().add(StudyBuddyApplication.class.getResource("dark-mode.css").toExternalForm());
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
