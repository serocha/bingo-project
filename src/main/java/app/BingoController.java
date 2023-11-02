package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;

public class BingoController {  // controller for the GUI and backend
    @FXML
    private TilePane playingBoard;

    public void initialize() {
        for (int i=1; i<26; i++) {
            Pane pane = new Pane();
            pane.getStyleClass().add("cell");
            pane.setId("cell-" + i);
            if (i == 13) {
                Label label = new Label("FREE");
                label.layoutXProperty().bind(pane.widthProperty().subtract(label.widthProperty()).divide(2));
                label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));
                label.setFont(new Font(20));
                pane.getChildren().add(label);
            }
            playingBoard.getChildren().add(pane);
        }
    }
}