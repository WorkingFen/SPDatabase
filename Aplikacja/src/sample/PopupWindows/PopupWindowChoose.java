package sample.PopupWindows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindowChoose {
    static private int answer;

    public static int display(String message, String title, int minWidth){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(minWidth);


        Label label = new Label();
        label.setText(message);


        Button oldButton = new Button("Stary klient");
        Button newButton = new Button("Nowy klient");
        Button cancel = new Button("Anuluj");
        cancel.setOnAction(e ->{
            answer = 0;
            window.close();
        });
        oldButton.setOnAction(e ->{
            answer = 1;
            window.close();
        });
        newButton.setOnAction(e ->{
            answer = 2;
            window.close();
        });

        HBox bottom = new HBox(10);
        VBox layout = new VBox(10);

        bottom.getChildren().addAll(oldButton, newButton, cancel);

        layout.getChildren().addAll(label, bottom);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
