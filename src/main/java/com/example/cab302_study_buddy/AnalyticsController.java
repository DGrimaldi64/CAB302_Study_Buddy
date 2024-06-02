package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

// For SQL and GridPane, not yet implemented
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

public class AnalyticsController {

    @FXML
    private Label taskEstOvershoot;

    @FXML
    private Label aveHoursStudyDay;

    @FXML
    private Label aveHoursDaily;

    @FXML
    private Label aveHoursWeekly;

    @FXML
    private Label aveDaysWeekly;

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

//    @FXML
//    private GridPane gridPane;
//
//    private Connection connection;

//    @FXML
//    public void initialize() {
//        // How well you estimate time to complete tasks (over or underestimate)
//        // How many hours you typically study (per day + per week)
//        // Calendar heatmap of productivity, like GitHubs activity calendar
//        try {
//            // Connect to the database
//            connection = DatabaseConnection.getInstance();
//
//            // Populate heatmap with data from the database
//            populateHeatmap();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void populateHeatmap() throws SQLException {
//        // Query to retrieve activity data
//        String query = "SELECT week_number, day_of_week, activity_level FROM activity_data";
//
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                int weekNumber = resultSet.getInt("week_number");
//                int dayOfWeek = resultSet.getInt("day_of_week");
//                int activityLevel = resultSet.getInt("activity_level");
//
//                // Create a StackPane representing each day
//                StackPane cell = new StackPane();
//                cell.setPrefSize(40, 40); // Adjust size as needed
//
//                // Set background color based on activity level
//                cell.setStyle("-fx-background-color: " + getColorForActivityLevel(activityLevel) + ";");
//
//                // Add tooltip to show activity level
//                Tooltip tooltip = new Tooltip("Activity Level: " + activityLevel);
//                Tooltip.install(cell, tooltip);
//
//                // Add the cell to the grid pane
//                gridPane.add(cell, dayOfWeek - 1, weekNumber - 1); // Adjust indices as per your database (week starts from 1)
//            }
//        }
//    }
//
//    private String getColorForActivityLevel(int activityLevel) {
//        // You can define your own color mapping based on activity level
//        // For simplicity, let's use a gradient of blue
//        double hue = Math.min(240, activityLevel * 30); // Maximum hue is 240 (blue)
//        return String.format("hsl(%d, 100%%, 50%%)", (int)hue);
//    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) taskEstOvershoot.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    @FXML
    protected void onCalculateGPAClick() throws IOException {
        // calculate GPA required per unit
        String result = GPATarget.getText();
    }

    // Shows current GPA and has GPA calculator which separates your current units into assessment pieces with weighting
    //     It will have the grade you show aim to beat for each assessment item
}
