package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BingoApp extends Application {  // the main application

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BingoApp.class.getResource("bingo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Agbalumo&display=swap");

        stage.setTitle("Animal Bingo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}