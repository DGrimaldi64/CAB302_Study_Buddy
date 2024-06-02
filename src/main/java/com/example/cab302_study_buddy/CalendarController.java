package com.example.cab302_study_buddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    private LocalDate currentDate;
    private EventManager eventManager = new EventManager("events.txt");

    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private Label monthYearLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox upcomingEventsBox;

    private Label[] dayLabels = new Label[42];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentDate = LocalDate.now();

        initializeMonthComboBox();
        initializeYearComboBox();
        populateCalendar();
        refreshUpcomingEvents();
    }

    private void initializeMonthComboBox() {
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        );
        monthComboBox.setValue(currentDate.getMonth().toString());
        monthComboBox.setOnAction(e -> {
            String selectedMonth = monthComboBox.getValue();
            currentDate = currentDate.withMonth(Month.valueOf(selectedMonth.toUpperCase()).getValue());
            refreshCalendar();
            refreshUpcomingEvents();
            updateMonthYearLabel();
        });
    }


    private void initializeYearComboBox() {
        yearComboBox.getItems().addAll(
                2022, 2023, 2024, 2025 // Modify with your desired range
        );
        yearComboBox.setValue(currentDate.getYear());
        yearComboBox.setOnAction(e -> {
            int selectedYear = yearComboBox.getValue();
            currentDate = currentDate.withYear(selectedYear);
            refreshCalendar();
            refreshUpcomingEvents();
            updateMonthYearLabel();
        });
    }

    private void updateMonthYearLabel() {
        String selectedMonth = monthComboBox.getValue();
        int selectedYear = yearComboBox.getValue();
        monthYearLabel.setText(selectedMonth + " " + selectedYear);
    }

    private void populateCalendar() {
        gridPane.getChildren().removeAll(dayLabels);

        String monthYearText = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        monthYearLabel.setText(monthYearText);

        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < 7; i++) {
            Label dayOfWeekLabel = new Label(daysOfWeek[i]);
            dayOfWeekLabel.setMinWidth(40);
            dayOfWeekLabel.setStyle("-fx-font-weight: bold");
            gridPane.add(dayOfWeekLabel, i, 2);
        }

        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int daysInMonth = currentDate.lengthOfMonth();
        int dayOfMonth = 1;

        int startRow = 3;
        int startCol = startDayOfWeek - 1;

        for (int row = startRow; row < 8; row++) {
            for (int col = 0; col < 7; col++) {
                if ((row == startRow && col < startCol) || dayOfMonth > daysInMonth) {
                    gridPane.add(new Label(), col, row);
                } else {
                    LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayOfMonth);
                    String event = eventManager.getEvent(date);

                    Label dayLabel = new Label(String.valueOf(dayOfMonth));
                    dayLabel.setMinSize(40, 40);
                    dayLabel.setAlignment(Pos.CENTER);
                    if (event != null && !event.isEmpty()) {
                        dayLabel.setStyle("-fx-background-color: rgba(135, 206, 250, 0.5); -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 5px;");
                    }
                    dayLabel.setOnMouseClicked(e -> handleDateClick(date)); // Modified to pass date directly
                    dayLabel.setUserData(date);

                    gridPane.add(dayLabel, col, row);
                    dayLabels[(row - 3) * 7 + col] = dayLabel;
                    dayOfMonth++;
                }
            }
        }
    }

    private void refreshCalendar() {
        populateCalendar();
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
    }



    private void refreshUpcomingEvents() {
        upcomingEventsBox.getChildren().clear();

        // Display upcoming events for the current month
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        long days = ChronoUnit.DAYS.between(firstDayOfMonth, lastDayOfMonth.plusDays(1));
        for (int i = 0; i < days; i++) {
            LocalDate date = firstDayOfMonth.plusDays(i);
            String event = eventManager.getEvent(date);
            if (event != null && !event.isEmpty()) {
                Label eventLabel = new Label(date.toString() + ": " + event);
                upcomingEventsBox.getChildren().add(eventLabel);
            }
        }

        if (upcomingEventsBox.getChildren().isEmpty()) {
            Label noEventsLabel = new Label("No upcoming events.");
            upcomingEventsBox.getChildren().add(noEventsLabel);
        }
    }

    private void handleDateClick(LocalDate date) {
        // Check if an event exists for the clicked date
        String event = eventManager.getEvent(date);
        if (event != null && !event.isEmpty()) {
            // An event exists for this date, ask for confirmation before deleting it
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Event Confirmation");
            alert.setContentText("An event exists for this date. Are you sure you want to delete it?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed deletion, delete the event
                eventManager.deleteEvent(date);
                refreshUpcomingEvents(); // Update upcoming events box
                refreshCalendar(); // Update calendar
            }
        }

        else {
            // No event exists for this date, proceed to add event
            // Create a new window (Stage) for adding event
            Stage addEventStage = new Stage();
            addEventStage.setTitle("Add Event");

            // Create components for the add event window
            Label dateLabel = new Label("Date: " + date);
            TextField eventTextField = new TextField();
            Button addButton = new Button("Add");
            addButton.setOnAction(e -> {
                String eventDescription = eventTextField.getText();
                eventManager.addEvent(date, eventDescription);
                refreshUpcomingEvents(); // Update upcoming events box
                refreshCalendar();
                addEventStage.close(); // Close the window after adding the event
            });

            // Create layout for the add event window
            VBox addEventLayout = new VBox(10);
            addEventLayout.getChildren().addAll(dateLabel, eventTextField, addButton);
            addEventLayout.setAlignment(Pos.CENTER);
            addEventLayout.setPadding(new Insets(20));

            // Create the scene and set it to the stage
            Scene addEventScene = new Scene(addEventLayout, 300, 200);
            addEventStage.setScene(addEventScene);

            // Show the add event window
            addEventStage.show();
        }
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
    }
}