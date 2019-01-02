package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupWindowEdit {
    static private String[] array;

    public static String[] display(String [] setPromptArray, String title, String message, int minWidth) throws IOException {

        array = new String[setPromptArray.length];

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(minWidth);


        Label label = new Label();
        label.setText(message);

        TextField []  text = new TextField[setPromptArray.length];
        for(int i = 0; i < array.length; i++)
        {
            text[i] = new TextField();
            text[i].setPromptText(setPromptArray[i]);
        }

        Button okButton = new Button("Ok");
        okButton.setOnAction(e ->{
            for(int i = 0; i < array.length; i++)
            {
                array[i] = text[i].getText();

            }
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.getChildren().addAll(text);
        layout.getChildren().addAll(okButton);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return array;
    }

}
