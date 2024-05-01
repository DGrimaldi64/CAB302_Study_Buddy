module com.example.cab302_study_buddy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.cab302_study_buddy to javafx.fxml;
    exports com.example.cab302_study_buddy;
}