
module com.example.lasertagproject {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires okhttp3;
    requires javafx.media;
    requires javafx.swing;
    // opens com.example.splashscreen to javafx.fxml;
    // exports com.example.splashscreen;
    opens com.example.lasertagproject to javafx.fxml;
    exports com.example.lasertagproject;
}
