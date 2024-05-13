package com.test_calender;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private final Map<LocalDate, String> events;
    private final String eventsFile;

    public EventManager(String eventsFile) {
        this.eventsFile = eventsFile;
        events = new HashMap<>();
        loadEvents(); // Load events from the file when this file is initialized
    }


    public void addEvent(LocalDate date, String eventDescription) {
        events.put(date, eventDescription);
        saveEvents();
        refreshUpcomingEvents();
    }

    public void editEvent(LocalDate date, String newEventDescription) {
        if (events.containsKey(date)) {
            events.put(date, newEventDescription);
            saveEvents();
            refreshUpcomingEvents();
        }
    }

    public String getEvent(LocalDate date) {
        return events.getOrDefault(date, "");
    }

    public boolean hasEvent(LocalDate date) {
        return events.containsKey(date);
    }

    public void deleteEvent(LocalDate date) {
        events.remove(date);
        saveEvents(); // Save changes to the events file
        refreshUpcomingEvents();
    }

    private void loadEvents() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(eventsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0]);
                String eventDescription = parts[1];
                events.put(date, eventDescription);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEvents() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(eventsFile));
            for (Map.Entry<LocalDate, String> entry : events.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshUpcomingEvents() {
        // Can implement the logic to refresh the upcoming events here
        // Let's just print a message
        System.out.println("Refreshing upcoming events...");
    }
}
