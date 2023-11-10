package app;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BingoController {  // controller for the GUI
    // fields
    private final ArrayList<String> IMAGES = new ArrayList<>(Arrays.asList(
            "antelope", "bear", "bird", "bunny", "cat",
            "chameleon", "cheetah", "cobra", "coral-snake", "dog",
            "elephant", "gecko", "giraffe", "goldfish", "guinea-pig",
            "hedgehog", "horse", "llama", "owl", "raven",
            "rhino", "shark", "sheep", "turtle", "whale", "zebra"
    ));
    public FlowPane root;

    // getters/setters
    public ArrayList<String> getIMAGES() {
        return IMAGES;
    }

    // FXML components
    @FXML
    private FlowPane card1;
    @FXML
    private FlowPane card2;
    @FXML
    private FlowPane card3;
    @FXML
    private FlowPane card4;
    private final ArrayList<FlowPane> bingoCards = new ArrayList<>(4);

    @FXML
    private TilePane board1;
    @FXML
    private TilePane board2;
    @FXML
    private TilePane board3;
    @FXML
    private TilePane board4;
    private final ArrayList<TilePane> playingBoards = new ArrayList<>(4);

    @FXML
    private Label tab1;
    @FXML
    private Label tab2;
    @FXML
    private Label tab3;
    @FXML
    private Label tab4;
    private final ArrayList<Label> tabs = new ArrayList<>(4);

    // methods
    public ImageView addImageToPane(String imageName) {  // util to set the image to a pane
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

    public ArrayList<String> shuffle() {  // shuffle the image order
        Random rand = new Random();
        ArrayList<String> shuffledArray = new ArrayList<>(getIMAGES());

        for (int i = 0; i < shuffledArray.size(); i++) {
            int randomIndexToSwap = rand.nextInt(shuffledArray.size());
            String temp = shuffledArray.get(randomIndexToSwap);
            shuffledArray.set(randomIndexToSwap, shuffledArray.get(i));
            shuffledArray.set(i, temp);
        }
        return shuffledArray;
    }

    public void initialize() {  // set up the boards to start
        bingoCards.addAll(List.of(card1, card2, card3, card4));
        playingBoards.addAll(List.of(board1, board2, board3, board4));
        tabs.addAll(List.of(tab1, tab2, tab3, tab4));

        for (int i=0; i<4; i++) {
            ArrayList<String> board = shuffle();
            drawBoard(playingBoards.get(i), board);
            bingoCards.get(i).toBack();
            if (i == 0)
                tabs.get(i).getStyleClass().add("selected");
            else
                tabs.get(i).getStyleClass().add("unselected");
        }
    }

    public void drawBoard(TilePane boardGUI, ArrayList<String> board) {  // render a board
        for (int i=1; i<26; i++) {
            StackPane pane = new StackPane();
            pane.getStyleClass().add("cell");
            pane.setId("cell-" + i);
            pane.setAlignment(Pos.CENTER);
            if (i == 13) {
                pane.getChildren().add(addImageToPane("star"));
                pane.getStyleClass().add("cell-selected");
            } else {
                pane.getChildren().add(addImageToPane(board.get(i)));
            }
            boardGUI.getChildren().add(pane);
        }
    }

    public void update(ArrayList<String> board) {
        // dummy function where I'll update the board
    }

    public void selectBoard(MouseEvent mouseEvent) {
        Label tab = (Label)mouseEvent.getSource();

        int i = 0;
        for (Label elem : tabs) {
            elem.getStyleClass().removeAll(List.of("selected","unselected"));
            if (elem == tab)
                bingoCards.get(i).toFront();
            else
                elem.getStyleClass().add("unselected");
            i++;
        }
        tab.getStyleClass().add("selected");
    }
}