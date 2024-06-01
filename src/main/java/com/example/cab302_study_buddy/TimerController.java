package com.example.cab302_study_buddy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Contains all logic required for the timer page functionality
 */
public class TimerController {

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

    private void clearTimer() {
        // set time to 00:00:00

        timerDisplay.setText("00:00:00");

        hoursInput.setText("00");
        minutesInput.setText("00");
        secondsInput.setText("00");

        hourSlider.setValue(0);
        minuteSlider.setValue(0);
        secondSlider.setValue(0);
    }

    private String FormatTime(TextField time) {
        if (time.getLength() == 2) {
            return time.getText();
        }
        else if (time.getLength() == 1) {
            return "0" + time.getText();
        }
        else {
            return "00";
        }
    }

    private String FormatTime(int timeInt) {
        String time = Integer.toString(timeInt);
        if (time.length() == 2) {
            return time;
        }
        else if (time.length() == 1) {
            return "0" + time;
        }
        else {
            return "00";
        }
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage)timerDisplay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
    }

    @FXML
    protected void onEnterClick() throws IOException {
        // start the timer

        stopCheck = false;

        timerControls.setVisible(true);
        setTime.setVisible(false);
        enterBtn.setVisible(false);


        // make method for this
        String hours = FormatTime(hoursInput);

        String minutes = FormatTime(minutesInput);

        String seconds = FormatTime(secondsInput);

        timerDisplay.setText(hours + ":" + minutes + ":" + seconds);

        int convertedSeconds = Integer.parseInt(seconds);
        int convertedMinutes = Integer.parseInt(minutes);
        int convertedHours = Integer.parseInt(hours);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int convertedTime = convertedHours * 3600 + convertedMinutes * 60 + convertedSeconds;

            @Override
            public void run() {
                // run timer
                Platform.runLater(() -> {
                    // Decrement seconds

                    if (stopCheck) {
                        clearTimer();
                        timer.cancel();
                        timer.purge();
                    }

                    else if (!pauseCheck) {
                        if (convertedTime > 0) {
                            convertedTime--;
                            String newSeconds = FormatTime(convertedTime % 60);
                            String newMinutes = FormatTime((convertedTime % 3600 - Integer.parseInt(newSeconds)) / 60);
                            String newHours = FormatTime((convertedTime - ((Integer.parseInt(newMinutes) * 60) - Integer.parseInt(newSeconds))) / 3600);
                            String newTime = newHours + ":" + newMinutes + ":" + newSeconds;

                            timerDisplay.setText(newTime);
                        }

                        else { // timer finished
                            clearTimer();

                            // alert user

                            setPauseBtn.setText("Set");

                            timer.cancel();
                            timer.purge();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    @FXML
    protected void onSetPauseClick() throws IOException {
        // set time on timer

        if (Objects.equals(setPauseBtn.getText(), "Resume")) {
            // resume timer

            pauseCheck = false;

            setPauseBtn.setText("Pause");
        }

        else if (Objects.equals(setPauseBtn.getText(), "Pause")) {
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

            if (newValue > 99) {
                newValue = newChar;
            }

            hourSlider.adjustValue((int)newValue);
        }

        catch (NumberFormatException e) {

            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        hoursInput.setText(FormatTime((int)hourSlider.getValue()));
        hoursInput.positionCaret(2);
    }

    @FXML
    protected void onHoursUpClick() throws IOException {
        // increment timer

        if (hourSlider.getValue() == 99) {
            hourSlider.setValue(0);
        }

        else {
            hourSlider.increment();
        }

        hoursInput.setText(FormatTime((int)hourSlider.getValue()));
    }

    @FXML
    protected void onHoursDownClick() throws IOException {
        // decrement timer

        if (hourSlider.getValue() == 0) {
            hourSlider.setValue(99);
        }

        else {
            hourSlider.decrement();
        }

        hoursInput.setText(FormatTime((int)hourSlider.getValue()));
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

        minutesInput.setText(FormatTime((int)minuteSlider.getValue()));
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

        minutesInput.setText(FormatTime((int)minuteSlider.getValue()));
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

        minutesInput.setText(FormatTime((int)minuteSlider.getValue()));
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

        secondsInput.setText(FormatTime((int)secondSlider.getValue()));
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

        secondsInput.setText(FormatTime((int)secondSlider.getValue()));
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

        secondsInput.setText(FormatTime((int)secondSlider.getValue()));
    }

    private boolean minimized = false;

    @FXML
    private Scene scene;

    @FXML
    private Stage stage;

//    @FXML
//    protected void onMiniTimerClick() throws IOException {
//
//        if (minimized) {
//            scene.getWindow().setWidth(1280);
//            scene.getWindow().setHeight(720);
//            stage.setAlwaysOnTop(false);
//            stage.setScene(scene);
//            System.out.println("Min = false");
//        }
//
//        else {
//            scene.getWindow().setWidth(320);
//            scene.getWindow().setHeight(320);
//            stage.setAlwaysOnTop(true);
//            stage.setScene(scene);
//            System.out.println("Min = true");
//        }
//
//        minimized = !minimized;
//    }
}