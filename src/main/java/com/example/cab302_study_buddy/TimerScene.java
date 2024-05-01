package com.example.cab302_study_buddy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TimerScene {

    // global variables
    private boolean stopCheck = false;
    private boolean pauseCheck = false;

    @FXML
    private Label timerDisplay;

    @FXML
    private HBox timerControls;

    @FXML
    private HBox setTime;

    @FXML
    private Button setPauseBtn;

    @FXML
    private Button enterBtn;

    @FXML
    private TextField hoursInput;

    @FXML
    private Slider hourSlider;

    @FXML
    private TextField minutesInput;

    @FXML
    private Slider minuteSlider;

    @FXML
    private TextField secondsInput;

    @FXML
    private Slider secondSlider;

    private void writeDisplay(String input) {
        timerDisplay.setText(input);
    }

    private void clearTimer() {
        // set time to 00:00:00

        hoursInput.setText("00");
        minutesInput.setText("00");
        secondsInput.setText("00");

        hourSlider.setValue(0);
        minuteSlider.setValue(0);
        secondSlider.setValue(0);
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage)timerDisplay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        stage.setScene(scene);
    }

    @FXML
    protected void onEnterClick() throws IOException {
        // start the timer

        stopCheck = false;

        timerControls.setVisible(true);
        setTime.setVisible(false);
        enterBtn.setVisible(false);

        String hours;
        if (hoursInput.getLength() == 2) {
            hours = hoursInput.getText();
        }
        else if (hoursInput.getLength() == 1) {
            hours = "0" + hoursInput.getText();
        }
        else {
            hours = "00";
        }

        String minutes;
        if (minutesInput.getLength() == 2) {
            minutes = minutesInput.getText();
        }
        else if (minutesInput.getLength() == 1) {
            minutes = "0" + minutesInput.getText();
        }
        else {
            minutes = "00";
        }

        String seconds;
        if (secondsInput.getLength() == 2) {
            seconds = secondsInput.getText();
        }
        else if (secondsInput.getLength() == 1) {
            seconds = "0" + secondsInput.getText();
        }
        else {
            seconds = "00";
        }

        timerDisplay.setText(hours + ":" + minutes + ":" + seconds);

        int convertedSeconds = Integer.parseInt(seconds);
        int convertedMinutes = Integer.parseInt(minutes);
        int convertedHours = Integer.parseInt(hours);

        Timer secondsTimer = new Timer();
        secondsTimer.scheduleAtFixedRate(new TimerTask() {
            int convertedTime = convertedHours * 3600 + convertedMinutes * 60 + convertedSeconds;
            @Override
            public void run() {
                // Decrement seconds

                if (stopCheck) {
                    secondsTimer.cancel();
                    secondsTimer.purge();
                }

                else if (!pauseCheck) {
                    if (convertedTime > 0) {
                        convertedTime--;
                        int newSeconds = convertedTime % 60;
                        int newMinutes = (convertedTime % 3600 - newSeconds) / 60;
                        int newHours = (convertedTime - ((newMinutes * 60) - newSeconds)) / 3600;
                        String newTime = newHours + ":" + newMinutes + ":" + newSeconds;

                        //timerDisplay.setText(newTime);

                        System.out.println(newTime);
                    }

                    else { // timer finished
                        clearTimer();

                        // timerDisplay.setText("Done");

                        System.out.println("Timer Finished");

                        secondsTimer.cancel();
                        secondsTimer.purge();
                    }
                }
            }
        }, 0, 1000);
    }

    @FXML
    protected void onSetPauseClick() throws IOException {
        // set time on timer

        if (setPauseBtn.getText() == "Resume") {
            // resume timer

            pauseCheck = false;

            setPauseBtn.setText("Pause");
        }

        else if (setPauseBtn.getText() == "Pause") {
            // pause timer

            pauseCheck = true;

            setPauseBtn.setText("Resume");
        }

        else { // == "Set"
            timerControls.setVisible(false);
            setTime.setVisible(true);
            enterBtn.setVisible(true);
            setPauseBtn.setText("Pause");
        }
    }

    @FXML
    protected void onStopClick() throws IOException {
        // stop timer
        clearTimer();
        stopCheck = true;
        setPauseBtn.setText("Set");
    }

    @FXML
    protected void onHoursType() throws IOException {
        // type in minutes

        try {
            String lastChar = hoursInput.getText().substring(hoursInput.getLength() - 1);

            double newChar = Integer.parseInt(lastChar);
            double prevLastChar = hourSlider.getValue() % 10;
            double newValue = (prevLastChar * 10) + newChar;

            if (newValue > 24) {
                newValue = newChar;
            }

            hourSlider.adjustValue((int)newValue);
        }

        catch (NumberFormatException e) {

            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        hoursInput.setText((int)hourSlider.getValue() + "");
        hoursInput.positionCaret(2);
    }

    @FXML
    protected void onHoursUpClick() throws IOException {
        // increment timer

        if (hourSlider.getValue() == 24) {
            hourSlider.setValue(0);
        }

        else {
            hourSlider.increment();
        }

        hoursInput.setText((int)hourSlider.getValue() + "");
    }

    @FXML
    protected void onHoursDownClick() throws IOException {
        // decrement timer

        if (hourSlider.getValue() == 0) {
            hourSlider.setValue(24);
        }

        else {
            hourSlider.decrement();
        }

        hoursInput.setText((int)hourSlider.getValue() + "");
    }

    @FXML
    protected void onMinutesType() throws IOException {
        // type in minutes

        try {
            String lastChar = minutesInput.getText().substring(minutesInput.getLength() - 1);

            double newChar = Integer.parseInt(lastChar);
            double prevLastChar = minuteSlider.getValue() % 10;
            double newValue = (prevLastChar * 10) + newChar;

            if (newValue > 59) {
                newValue = newChar;
            }

            minuteSlider.adjustValue((int)newValue);
        }

        catch (NumberFormatException e) {

            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        minutesInput.setText((int)minuteSlider.getValue() + "");
        minutesInput.positionCaret(2);
    }

    @FXML
    protected void onMinutesUpClick() throws IOException {
        // increment timer

        if (minuteSlider.getValue() == 59) {
            minuteSlider.setValue(0);
        }

        else {
            minuteSlider.increment();
        }

        minutesInput.setText((int)minuteSlider.getValue() + "");
    }

    @FXML
    protected void onMinutesDownClick() throws IOException {
        // decrement timer

        if (minuteSlider.getValue() == 0) {
            minuteSlider.setValue(59);
        }

        else {
            minuteSlider.decrement();
        }

        minutesInput.setText((int)minuteSlider.getValue() + "");
    }

    @FXML
    protected void onSecondsType() throws IOException {
        // type in minutes

        try {
            String lastChar = secondsInput.getText().substring(secondsInput.getLength() - 1);

            double newChar = Integer.parseInt(lastChar);
            double prevLastChar = secondSlider.getValue() % 10;
            double newValue = (prevLastChar * 10) + newChar;

            if (newValue > 59) {
                newValue = newChar;
            }

            secondSlider.adjustValue((int)newValue);
        }

        catch (NumberFormatException e) {

            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        secondsInput.setText((int)secondSlider.getValue() + "");
        secondsInput.positionCaret(2);
    }

    @FXML
    protected void onSecondsUpClick() throws IOException {
        // increment timer

        if (secondSlider.getValue() == 59) {
            secondSlider.setValue(0);
        }

        else {
            secondSlider.increment();
        }

        secondsInput.setText((int)secondSlider.getValue() + "");
    }

    @FXML
    protected void onSecondsDownClick() throws IOException {
        // decrement timer

        if (secondSlider.getValue() == 0) {
            secondSlider.setValue(59);
        }

        else {
            secondSlider.decrement();
        }

        secondsInput.setText((int)secondSlider.getValue() + "");
    }
}