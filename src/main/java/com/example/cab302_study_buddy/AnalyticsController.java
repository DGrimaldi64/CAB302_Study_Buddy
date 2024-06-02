package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static com.example.cab302_study_buddy.LoginController.current_user;

public class AnalyticsController {

    @FXML
    private Label taskEstOvershoot;

    @FXML
    private Label avgHoursDaily;

    @FXML
    private Label avgHoursWeekly;

    @FXML
    private Label avgDaysWeekly;

    @FXML
    private TextField GPATarget;

    @FXML
    private TextField creditsCompleted;

    @FXML
    private TextField creditsRemaining;

    @FXML
    private TextField currentGPA;

    @FXML
    private Label requiredGPAPerUnit;

    private Connection connection;
    private int currentUserId;

    /**
     * Method called when the FXML file is loaded
     */
    @FXML
    public void initialize() {
        // Connect to the database
        this.currentUserId = current_user.getId();
        connection = DatabaseConnection.getInstance();

        // Calculate and display statistics
        calculateStatistics();
    }

    /**
     * Method to calculate and display various statistics
     */
    private void calculateStatistics() {
        try {
            // Task Estimation Overshoot
            String query = "SELECT SUM(expected_completion_time - current_time_worked) AS total_overshoot, " +
                    "SUM(expected_completion_time) AS total_expected " +
                    "FROM tasks WHERE is_active IS FALSE AND user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserId); // Set the user_id value
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double totalOvershoot = resultSet.getDouble("total_overshoot");
                double totalExpected = resultSet.getDouble("total_expected");
                double percentageOvershoot = totalExpected > 0 ? (totalOvershoot / totalExpected) * 100 : 0;
                taskEstOvershoot.setText(String.format("%.2f%%", percentageOvershoot));
            }

            // Average Hours Studied Daily
            query = "SELECT date, SUM(hours_worked) AS total_time FROM record WHERE user_id = ? GROUP BY date";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserId); // Set the user_id value
            resultSet = preparedStatement.executeQuery();
            int daysStudied = 0;
            int totalMinutesStudied = 0;
            while (resultSet.next()) {
                daysStudied++;
                totalMinutesStudied += resultSet.getInt("total_time") * 60; // converting hours to minutes
            }
            double averageHoursDaily = daysStudied > 0 ? (double) totalMinutesStudied / daysStudied / 60 : 0;
            avgHoursDaily.setText(String.format("%.2f hours", averageHoursDaily));

            // Average Hours Studied Weekly and Average Days of Study Per Week
            query = "SELECT date, SUM(hours_worked) AS total_time FROM record WHERE user_id = ? GROUP BY date";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserId); // Set the user_id value
            resultSet = preparedStatement.executeQuery();
            int[] weeklyMinutes = new int[52]; // 52 weeks in a year
            int[] weeklyDays = new int[52];
            while (resultSet.next()) {
                long epochMillis = resultSet.getLong("date");
                Instant instant = Instant.ofEpochMilli(epochMillis);
                LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                int weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                weeklyMinutes[weekOfYear - 1] += resultSet.getInt("total_time") * 60; // converting hours to minutes
                weeklyDays[weekOfYear - 1]++;
            }
            int weeksStudied = 0;
            int totalMinutesWeekly = 0;
            int totalDaysWeekly = 0;
            for (int i = 0; i < 52; i++) {
                if (weeklyMinutes[i] > 0) {
                    weeksStudied++;
                    totalMinutesWeekly += weeklyMinutes[i];
                    totalDaysWeekly += weeklyDays[i];
                }
            }
            double averageHoursWeekly = weeksStudied > 0 ? (double) totalMinutesWeekly / weeksStudied / 60 : 0;
            double averageDaysWeekly = weeksStudied > 0 ? (double) totalDaysWeekly / weeksStudied : 0;
            avgHoursWeekly.setText(String.format("%.2f hours", averageHoursWeekly));
            avgDaysWeekly.setText(String.format("%.2f days", averageDaysWeekly));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to handle the back button click event
     */
    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) taskEstOvershoot.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
    }

    /**
     * Method to handle the calculate GPA button click event
     */
    @FXML
    protected void onCalculateGPAClick() throws IOException {
        // calculate GPA required per unit
        try {
            double TargetGPA = Double.parseDouble(GPATarget.getText());
            double CreditsCompleted = Double.parseDouble(creditsCompleted.getText());
            double CreditsRemaining = Double.parseDouble(creditsRemaining.getText());
            double CurrentGPA = Double.parseDouble(currentGPA.getText());

            double result = (TargetGPA * (CreditsCompleted + CreditsRemaining) - CreditsCompleted * CurrentGPA) / CreditsRemaining;

            if (result <= 7 && result >= 1) {
                requiredGPAPerUnit.setText(String.format("Required GPA Per Unit: %.2f", result));
            }
            else {
                requiredGPAPerUnit.setText("Impossible to Reach Target");
            }
        } catch (NumberFormatException e) {
            showAlert("Incorrectly Formatted Data: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Method to show an alert with a given message
     */
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
