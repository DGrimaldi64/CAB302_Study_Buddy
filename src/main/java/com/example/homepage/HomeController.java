package com.example.homepage;

import com.example.cab302_study_buddy.StudyBuddyApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onTasksClick() throws IOException {
        // change scene to Tasks
    }

    @FXML
    protected void onCalendarClick() throws IOException {
        // change scene to Calendar
    }

    @FXML
    protected void onTimerClick() throws IOException {
        // change scene to Timer
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("timer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        stage.setScene(scene);
    }

    @FXML
    protected void onRewardsClick() throws IOException {
        // change scene to Rewards
    }

    @FXML
    protected void onStudyHelperClick() throws IOException {
        // change scene to Study Helper
    }

    @FXML
    protected void onAnalyticsClick() throws IOException {
        // change scene to Analytics
    }

    @FXML
    protected void onAccessibilityClick() throws IOException {
        // change scene to Accessibility
    }

    @FXML
    protected void onSettingsClick() throws IOException {
        // change scene to Settings
    }
}