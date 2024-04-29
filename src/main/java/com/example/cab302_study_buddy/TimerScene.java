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

    @FXML
    protected void onEnterClick() throws IOException {
        // start the timer

        timerControls.setVisible(true);
        setTime.setVisible(false);
        enterBtn.setVisible(false);
        setPauseBtn.setText("Pause");
    }

    @FXML
    protected void onSetPauseClick() throws IOException {
        // set time on timer

        if (setPauseBtn.getText() == "Set") {
            timerControls.setVisible(false);
            setTime.setVisible(true);
            enterBtn.setVisible(true);
        }

        else if (setPauseBtn.getText() == "Pause") {
            // pause timer

            setPauseBtn.setText("Resume");
        }

        else { // == "Resume"
            // resume timer

            setPauseBtn.setText("Pause");
        }
    }

    @FXML
    protected void onStopClick() throws IOException {
        // stop timer

        // set time to 00:00:00
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

        hourSlider.increment();
        hoursInput.setText((int)hourSlider.getValue() + "");
    }

    @FXML
    protected void onHoursDownClick() throws IOException {
        // decrement timer

        hourSlider.decrement();
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
        secondSlider.increment();
        secondsInput.setText((int)secondSlider.getValue() + "");
    }

    @FXML
    protected void onSecondsDownClick() throws IOException {
        // decrement timer

        secondSlider.decrement();
        secondsInput.setText((int)secondSlider.getValue() + "");
    }
}