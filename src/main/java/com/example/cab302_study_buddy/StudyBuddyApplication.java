package com.example.cab302_study_buddy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class StudyBuddyApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Study Buddy");
        stage.setScene(scene);
        stage.show();
    }

     public static void main(String[] args) {launch(args);}


//    private static final User32 user32 = User32.INSTANCE;
//    private static final Kernel32 kernel32 = Kernel32.INSTANCE;
//    private static final Psapi psapi = Psapi.INSTANCE;
//
//    private static String lastWindowTitle = "";
//    private static long lastSwitchTime = System.currentTimeMillis();
//    private static Map<String, Long> timeSpent = new HashMap<>();
//
//    public static void main(String[] args) {
//        while (true) {
//            trackActiveWindow();
//            try {
//                Thread.sleep(1000); // Check every second
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void trackActiveWindow() {
//        HWND hwnd = user32.GetForegroundWindow();
//        if (hwnd == null) {
//            System.out.println("No active window.");
//            return;
//        }
//
//        char[] windowText = new char[512];
//        user32.GetWindowText(hwnd, windowText, 512);
//        String windowTitle = Native.toString(windowText);
//
//        DWORD processId = new DWORD();
//        user32.GetWindowThreadProcessId(hwnd, processId);
//        HANDLE processHandle = kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, processId.intValue());
//
//        char[] buffer = new char[1024];
//        psapi.GetModuleBaseNameW(processHandle, null, buffer, buffer.length);
//        String processName = Native.toString(buffer);
//
//        if (!lastWindowTitle.equals(windowTitle)) {
//            long currentTime = System.currentTimeMillis();
//            long timeSpentOnLastWindow = currentTime - lastSwitchTime;
//            timeSpent.put(lastWindowTitle, timeSpent.getOrDefault(lastWindowTitle, 0L) + timeSpentOnLastWindow);
//            lastSwitchTime = currentTime;
//            lastWindowTitle = windowTitle;
//        }
//
//        System.out.println("Active window title: " + windowTitle);
//        System.out.println("Active window process ID: " + processId);
//        System.out.println("Active window process name: " + processName);
//    }
}