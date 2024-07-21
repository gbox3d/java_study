module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;

    opens com.example to javafx.fxml;
    exports com.example;
}