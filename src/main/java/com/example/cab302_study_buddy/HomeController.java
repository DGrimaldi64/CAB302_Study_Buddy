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

    /**
     * Method to initialize the HomeController and load images into the ImageViews
     */
    public void initialize() {
        File tasksFile = new File("src/main/resources/images/tasks.png");
        Image tasksImage = new Image(tasksFile.toURI().toString());
        tasksImageView.setImage(tasksImage);
        tasksImageView.getStyleClass().add("border");

        File calendarFile = new File("src/main/resources/images/calendar.png");
        Image calendarImage = new Image(calendarFile.toURI().toString());
        calendarImageView.setImage(calendarImage);

        File timerFile = new File("src/main/resources/images/timer.png");
        Image timerImage = new Image(timerFile.toURI().toString());
        timerImageView.setImage(timerImage);

        File analyticsFile = new File("src/main/resources/images/analytics.png");
        Image analyticsImage = new Image(analyticsFile.toURI().toString());
        analyticsImageView.setImage(analyticsImage);

        File accessibilityFile = new File("src/main/resources/images/accessibility.png");
        Image accessibilityImage = new Image(accessibilityFile.toURI().toString());
        accessibilityImageView.setImage(accessibilityImage);

        File settingsFile = new File("src/main/resources/images/settings.png");
        Image settingsImage = new Image(settingsFile.toURI().toString());
        settingsImageView.setImage(settingsImage);
    }

    /**
     * Method to handle tasks button click event
     */
    @FXML
    protected void onTasksClick() throws IOException {
        // change scene to Tasks view
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    /**
     * Method to handle calendar button click event
     */
    @FXML
    protected void onCalendarClick() throws IOException {
        // change scene to Calendar view
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    /**
     * Method to handle timer button click event
     */
    @FXML
    protected void onTimerClick() throws IOException {
        // change scene to Timer view
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("timer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    /**
     * Method to handle analytics button click event
     */
    @FXML
    protected void onAnalyticsClick() throws IOException {
        // change scene to Analytics view
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("analytics-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    /**
     * Method to handle accessibility button click event
     */
    @FXML
    protected void onAccessibilityClick() throws IOException {
        // change scene to Accessibility view
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("accessibility-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    /**
     * Method to handle settings button click event
     */
    @FXML
    protected void onSettingsClick() throws IOException {
        // change scene to Settings view
        Stage stage = (Stage)welcomeText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }
}