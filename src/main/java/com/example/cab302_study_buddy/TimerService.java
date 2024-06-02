package com.example.cab302_study_buddy;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.cab302_study_buddy.TaskController.Task;
import static com.example.cab302_study_buddy.LoginController.current_user;

public class TimerService {

    private static TimerService instance;
    private Timer timer;
    private int convertedTime;
    private boolean stopCheck = false;
    private boolean pauseCheck = false;
    private boolean alertShown = false;
    private StringProperty timerDisplayProperty = new SimpleStringProperty("00:00:00");

    private TimerService() {
    }

    public static TimerService getInstance() {
        if (instance == null) {
            instance = new TimerService();
        }
        return instance;
    }

    public enum TimerState {
        RUNNING,
        PAUSED,
        STOPPED
    }

    private TimerState timerState;

    public TimerState getTimerState() {
        return timerState;
    }

    public StringProperty timerDisplayProperty() {
        return timerDisplayProperty;
    }

    public void startTimer(Task selectedTask, int hours, int minutes, int seconds) {
        stopTimer();
        stopCheck = false;
        pauseCheck = false;
        alertShown = false;
        timerState = TimerState.RUNNING;

        convertedTime = hours * 3600 + minutes * 60 + seconds;
        updateTimerDisplay();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int elapsedSeconds = 0;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (stopCheck) {
                        stopTimer();
                    } else if (!pauseCheck) {
                        if (convertedTime > 0) {
                            convertedTime--;
                            elapsedSeconds++;

                            // Update the time worked on the selected task
                            if (selectedTask != null) {
                                // Update the time worked on the task in the database
                                DatabaseHandler.updateTimeWorked(selectedTask.getTaskName(), current_user.getId(), elapsedSeconds);
                            }

                            // Check if 20 minutes elapsed and time left is more than 5 minutes
                            if (elapsedSeconds % (3) == 0 && convertedTime > 10) { //(20 * 60) == 0 && convertedTime > 60 * 5) { // set to 3 seconds for testing
                                pauseTimer(); // Pause timer
                                showAlert("Take a 5-minute break!"); // Show break alert

                                // Start a 5-minute break timer
                                Timer breakTimer = new Timer();
                                breakTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Platform.runLater(() -> {
                                            showAlert("Break time is over. Get back to study!");
                                            resumeTimer(); // Resume study timer
                                            breakTimer.cancel(); // Cancel break timer
                                        });
                                    }
                                }, // 5 * 60 * 1000); // 5 minutes in milliseconds
                                3 * 1000); // set to 3 seconds for testing
                            }

                            // Update timer display
                            updateTimerDisplay();
                        } else if (!alertShown) {
                            stopTimer();
                            showAlert("Timer finished!");
                            alertShown = true;
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        convertedTime = 0;
        updateTimerDisplay();
        timerState = TimerState.STOPPED;
    }

    public void pauseTimer() {
        pauseCheck = true;
        timerState = TimerState.PAUSED;
    }

    public void resumeTimer() {
        pauseCheck = false;
        timerState = TimerState.RUNNING;
    }

    private void updateTimerDisplay() {
        int hours = convertedTime / 3600;
        int minutes = (convertedTime % 3600) / 60;
        int seconds = convertedTime % 60;
        timerDisplayProperty.set(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Timer Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}