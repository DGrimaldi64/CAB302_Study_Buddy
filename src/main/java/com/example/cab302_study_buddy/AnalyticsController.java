package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

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
    private TextField creditsUntilDeadline;

    @FXML
    private TextField currentGPA;

    @FXML
    private Label requiredGPAPerUnit;

    @FXML
    public void initialize() {
        // How well you estimate time to complete tasks (over or underestimate)
        // How many hours you typically study (per day + per week)
        // Calendar heatmap of productivity, like GitHubs activity calendar
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) taskEstOvershoot.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
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
