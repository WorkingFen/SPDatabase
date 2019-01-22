package sample;

import JDBC.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static public JDBC jdbc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        jdbc = new JDBC();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Logowanie");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("ButtonStyles.css");

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
