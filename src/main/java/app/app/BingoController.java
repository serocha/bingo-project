package app.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BingoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}