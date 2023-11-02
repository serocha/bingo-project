module app.app {
    requires javafx.controls;
    requires javafx.fxml;

    exports app;
    opens app to javafx.fxml;
}