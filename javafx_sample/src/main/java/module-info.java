module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;
    requires java.desktop;
    requires org.bytedeco.javacv;
    requires org.bytedeco.ffmpeg;

    opens com.example to javafx.fxml;
    exports com.example;
}
