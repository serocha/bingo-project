module app.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.app to javafx.fxml;
    exports app.app;
}