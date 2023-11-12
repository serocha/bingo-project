package app;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.*;

public class BingoController {  // controller for the GUI
    // fields
    private final ArrayList<String> IMAGE_LIST = new ArrayList<>(Arrays.asList(
            "antelope", "bear", "bunny", "cat", "chameleon",
            "cheetah", "cobra", "coral-snake", "dog", "elephant",
            "gecko", "giraffe", "goldfish", "guinea-pig", "hedgehog",
            "horse", "llama", "owl", "raven", "rhino",
            "shark", "sheep", "turtle", "whale", "zebra"
    ));  // there's 1 extra picture, but I like the images so I'm leaving it in

    private ArrayList<String> imagesRemaining;

    private final ArrayList<Board> boards = new ArrayList<>();

    // getters/setters
    public ArrayList<String> getIMAGE_LIST() {
        return IMAGE_LIST;
    }

    public ArrayList<Board> getBoards() {
        return boards;
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
    private TilePane boardGUI_1;
    @FXML
    private TilePane boardGUI_2;
    @FXML
    private TilePane boardGUI_3;
    @FXML
    private TilePane boardGUI_4;
    private final ArrayList<TilePane> boardGUIs = new ArrayList<>(4);

    @FXML
    private Label tab1;
    @FXML
    private Label tab2;
    @FXML
    private Label tab3;
    @FXML
    private Label tab4;
    private final ArrayList<Label> tabs = new ArrayList<>(4);

    @FXML
    private Button updateButton;
    @FXML
    private Label winLabel;

    @FXML
    public void initialize() {
        /*
            Sets up GUI and populates the backend Board.
         */
        boardGUIs.addAll(List.of(boardGUI_1, boardGUI_2, boardGUI_3, boardGUI_4));
        boards.addAll(List.of(
                new Board("Board A"),
                new Board("Board B"),
                new Board("Board C"),
                new Board("Board D")));
        tabs.addAll(List.of(tab1, tab2, tab3, tab4));
        bingoCards.addAll(List.of(card1, card2, card3, card4));

        for (int i=0; i<4; i++) {
            ArrayList<String> images = shuffle();

            getBoards().get(i).populateBoard(images);  // board
            drawBoardGUI(boardGUIs.get(i), images);    // board GUI
            bingoCards.get(i).toBack();                // StackPane order

            if (i == 0)                                // initialize tabs
                tabs.get(i).getStyleClass().add("selected");
            else
                tabs.get(i).getStyleClass().add("unselected");
        }

        ArrayList<String> images = shuffle();
        imagesRemaining = new ArrayList<>(IMAGE_LIST);  // set state
    }

    // methods
    private ImageView addImageToPane(String imageName) {
        /*
            Utility that sets an image to a pane based on the image name
         */
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

    private ArrayList<String> shuffle() {
        /*
            Shuffles the image order of an ArrayList of image names.
         */
        Random rand = new Random();
        ArrayList<String> shuffledArray = new ArrayList<>(getIMAGE_LIST());

        for (int i = 0; i < shuffledArray.size(); i++) {
            int randomIndexToSwap = rand.nextInt(shuffledArray.size());
            String temp = shuffledArray.get(randomIndexToSwap);
            shuffledArray.set(randomIndexToSwap, shuffledArray.get(i));
            shuffledArray.set(i, temp);
        }
        return shuffledArray;
    }

    private void drawBoardGUI(TilePane boardGUI, ArrayList<String> imageNames) {
        /*
            Renders a TilePane board element based on the ArrayList of image names.
         */
        for (int i=0; i<25; i++) {
            StackPane pane = new StackPane();
            pane.getStyleClass().add("cell");
            pane.setId("cell-" + i);
            pane.setAlignment(Pos.CENTER);
            if (i == 12) {
                pane.getChildren().add(addImageToPane("star"));
                pane.getStyleClass().add("cell-selected");
            } else {
                pane.getChildren().add(addImageToPane(imageNames.get(i)));
            }
            boardGUI.getChildren().add(pane);
        }
    }

    private void updateBoardGUI(Board board, TilePane boardGUI) {  // update a board GUI based on changes to a Board instance
        ObservableList<Node> cellsGUI = boardGUI.getChildren();
        Cell[] cells = board.flattenBoard();
        for (int i=0; i<cells.length; i++) {
            if (cells[i].isPicked() && !cellsGUI.get(i).getStyleClass().contains("cell-selected")) {
                cellsGUI.get(i).getStyleClass().add("cell-selected");
            }
        }
    }

    private String drawPicture() {
        int index = (int) (Math.random() * imagesRemaining.size());
        String image = imagesRemaining.get(index);
        imagesRemaining.remove(index);
        return image;
    }

    private void resetGame() {
        winLabel.setText("");
        updateButton.setText("Next Animal!");
        for (Board board : boards) {
            board.resetBoard();
        }

        for (int i=0; i<4; i++) {
            boardGUIs.get(i).getChildren().clear();
            ArrayList<String> images = shuffle();
            getBoards().get(i).populateBoard(images);  // board
            drawBoardGUI(boardGUIs.get(i), images);    // board GUI
        }

        imagesRemaining = new ArrayList<>(IMAGE_LIST);  // set state
    }

    public void update() {
        if (!winLabel.getText().isEmpty())
            resetGame();
        else {
            String picture = drawPicture();

            for (int i = 0; i < getBoards().size(); i++) {
                Board board = getBoards().get(i);
                board.markCell(picture);
                updateBoardGUI(board, boardGUIs.get(i));

                if (board.evaluateWinner()) {
                    winLabel.setText("Winner! " + board.getName() + " has BINGO!");
                    updateButton.setText("Start New Game");
                }
            }
        }
    }

    public void selectBoard(MouseEvent mouseEvent) {
        /*
            Allows selecting GUI board tabs to switch between viewing boards.
         */
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