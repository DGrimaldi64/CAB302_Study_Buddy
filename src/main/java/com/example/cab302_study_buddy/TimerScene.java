package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TimerScene {

    @FXML
    private Label timerTitleText;

    @FXML
    private Label timerCounterText;

    @FXML
    protected void onSetClick() throws IOException {
        // set time on timer
    }

    @FXML
    protected void onStopClick() throws IOException {
        // stop timer
    }
}