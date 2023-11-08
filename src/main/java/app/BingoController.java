package app;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class BingoController {  // controller for the GUI and backend
    @FXML
    private TilePane playingBoard;

    private final ArrayList<String> images = new ArrayList<>(Arrays.asList(
            "antelope", "bear", "bird", "bunny", "cat",
            "chameleon", "cheetah", "cobra", "coral-snake", "dog",
            "elephant", "gecko", "giraffe", "goldfish", "guinea-pig",
            "hedgehog", "horse", "llama", "owl", "raven",
            "rhino", "shark", "sheep", "turtle", "whale", "zebra"
    ));

    public ImageView addImageToPane(String imageName) {
        try {
            String imagePath = "images/"+imageName+".png";  // Get the image file path
            File file = new File(imagePath);  // Create an Image object
            Image image = new Image(file.toURI().toString());

            ImageView imageView =  new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);
            return imageView;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageView();
    }

    public void initialize() {  // creating cells, could create a custom class extending Pane if needed

        for (int i=1; i<26; i++) {
            StackPane pane = new StackPane();
            pane.getStyleClass().add("cell");
            pane.setId("cell-" + i);
            pane.setAlignment(Pos.CENTER);
            if (i == 13) {
                pane.getChildren().add(addImageToPane("star"));
                pane.getStyleClass().add("cell-selected");
            } else {
                pane.getChildren().add(addImageToPane(images.get(i)));
            }
            playingBoard.getChildren().add(pane);
        }
    }
}