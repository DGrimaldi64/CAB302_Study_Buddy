package com.test_calender;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.DayOfWeek;
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
    private Label monthYearLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox upcomingEventsBox;
    @FXML
    private Label[] dayLabels = new Label[42];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentDate = LocalDate.now();

        initializeMonthComboBox();
        populateCalendar();
        refreshUpcomingEvents();
    }

    private void initializeMonthComboBox() {
        // Month selection ComboBox
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        );
        monthComboBox.setValue(currentDate.getMonth().toString());
        monthComboBox.setOnAction(e -> {
            String selectedMonth = monthComboBox.getValue();
            currentDate = currentDate.withMonth(Month.valueOf(selectedMonth.toUpperCase()).getValue());
            refreshCalendar();
            refreshUpcomingEvents();
            monthComboBox.setValue(selectedMonth); // Set the value of the choice box
        });

        // Year selection ComboBox
        ComboBox<Integer> yearComboBox = new ComboBox<>();
        int currentYear = currentDate.getYear();
        yearComboBox.getItems().addAll(
                currentYear - 1, currentYear, currentYear + 1
        );
        yearComboBox.setValue(currentYear);
        yearComboBox.setOnAction(e -> {
            int selectedYear = yearComboBox.getValue();
            currentDate = currentDate.withYear(selectedYear);
            refreshCalendar();
            refreshUpcomingEvents();
            yearComboBox.setValue(selectedYear); // Set the value of the choice box
        });

        // Add the ComboBoxes to the gridPane
        gridPane.add(monthComboBox, 0, 0);
        gridPane.add(yearComboBox, 1, 0);
    }

    private void populateCalendar() {
        gridPane.getChildren().removeAll(dayLabels);

        String monthYearText = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        monthYearLabel.setText(monthYearText);

        // Add labels for days of the week
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
                    Label dayLabel = new Label(String.valueOf(dayOfMonth));
                    dayLabel.setMinWidth(40);
                    dayLabel.setOnMouseClicked(e -> handleDateClick(dayLabel));
                    LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayOfMonth);
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

    private void handleDateClick(Label dayLabel) {
        LocalDate date = (LocalDate) dayLabel.getUserData();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add/Edit Event");
        dialog.setHeaderText("Add/Edit event for " + date.toString());
        dialog.setContentText("Enter event description:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(eventDescription -> {
            if (eventManager.hasEvent(date)) {
                TextInputDialog editDialog = new TextInputDialog(eventManager.getEvent(date));
                editDialog.setTitle("Edit Event");
                editDialog.setHeaderText("Edit event for " + date.toString());
                editDialog.setContentText("Enter new event description:");

                Optional<String> editResult = editDialog.showAndWait();
                editResult.ifPresent(newEventDescription -> {
                    eventManager.editEvent(date, newEventDescription);
                    refreshUpcomingEvents();
                });
            } else {
                eventManager.addEvent(date, eventDescription);
                refreshUpcomingEvents();
            }
        });
    }

    private void refreshUpcomingEvents() {
        upcomingEventsBox.getChildren().clear();

        int daysInMonth = currentDate.lengthOfMonth();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(daysInMonth);
        refreshUpcomingEventsInRange(firstDayOfMonth, lastDayOfMonth);
    }


    private void refreshUpcomingEventsInRange(LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));

        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            String event = eventManager.getEvent(date);
            if (event != null && !event.isEmpty()) {
                Label eventLabel = new Label(date.toString() + ": " + event);
                System.out.println("Upcoming Event: " + eventLabel.getText()); // Print the upcoming event
                upcomingEventsBox.getChildren().add(eventLabel);
            }
        }

        if (upcomingEventsBox.getChildren().isEmpty()) {
            Label noEventsLabel = new Label("No upcoming events.");
            upcomingEventsBox.getChildren().add(noEventsLabel);
        }
    }
}