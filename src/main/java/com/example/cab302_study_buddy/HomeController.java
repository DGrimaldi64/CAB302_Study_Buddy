package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class HomeController {

    @FXML
    private ImageView tasksImageView;

    @FXML
    private ImageView calendarImageView;

    @FXML
    private ImageView timerImageView;

    @FXML
    private ImageView analyticsImageView;

    @FXML
    private ImageView accessibilityImageView;

    @FXML
    private ImageView settingsImageView;

    @FXML
    private Label welcomeText;

    public void initialize() {
        File tasksFile = new File("src/main/java/com/example/cab302_study_buddy/tasks.png");
        Image tasksImage = new Image(tasksFile.toURI().toString());
        tasksImageView.setImage(tasksImage);
        tasksImageView.getStyleClass().add("border");

        File calendarFile = new File("src/main/java/com/example/cab302_study_buddy/calendar.png");
        Image calendarImage = new Image(calendarFile.toURI().toString());
        calendarImageView.setImage(calendarImage);

        File timerFile = new File("src/main/java/com/example/cab302_study_buddy/timer.png");
        Image timerImage = new Image(timerFile.toURI().toString());
        timerImageView.setImage(timerImage);

        File analyticsFile = new File("src/main/java/com/example/cab302_study_buddy/analytics.png");
        Image analyticsImage = new Image(analyticsFile.toURI().toString());
        analyticsImageView.setImage(analyticsImage);

        File accessibilityFile = new File("src/main/java/com/example/cab302_study_buddy/accessibility.png");
        Image accessibilityImage = new Image(accessibilityFile.toURI().toString());
        accessibilityImageView.setImage(accessibilityImage);

        File settingsFile = new File("src/main/java/com/example/cab302_study_buddy/settings.png");
        Image settingsImage = new Image(settingsFile.toURI().toString());
        settingsImageView.setImage(settingsImage);
    }

    @FXML
    protected void onTasksClick() throws IOException {
        // change scene to Tasks
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        stage.setScene(scene);
    }

    @FXML
    protected void onCalendarClick() throws IOException {
        // change scene to Calendar
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        stage.setScene(scene);
    }

    @FXML
    protected void onTimerClick() throws IOException {
        // change scene to Timer
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("timer-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

// ADD these later if we have time
//    @FXML
//    protected void onRewardsClick() throws IOException {
//        // change scene to Rewards
//    }
//
//    @FXML
//    protected void onStudyHelperClick() throws IOException {
//        // change scene to Study Helper
//    }

    @FXML
    protected void onAnalyticsClick() throws IOException {
        // change scene to Analytics
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("analytics-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        stage.setScene(scene);
    }

    @FXML
    protected void onAccessibilityClick() throws IOException {
        // change scene to Accessibility
//        Stage stage = (Stage)welcomeText.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("accessibility-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
//        stage.setScene(scene);
    }

    @FXML
    protected void onSettingsClick() throws IOException {
        // change scene to Settings
//        Stage stage = (Stage)welcomeText.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("settings-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
//        stage.setScene(scene);
    }
}