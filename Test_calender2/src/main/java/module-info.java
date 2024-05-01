module com.test_calender {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.test_calender to javafx.fxml;
    exports com.test_calender;
}