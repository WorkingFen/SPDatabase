package sample.PopupWindows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindowAlert {
    static private boolean answer;

    public static boolean display(String message, String title, int minWidth){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(minWidth);


        Label label = new Label();
        label.setText(message);


        Button yesButton = new Button("Tak");
        Button noButton = new Button("Nie");
        yesButton.setOnAction(e ->{
            answer = true;
            window.close();
        });
        noButton.setOnAction(e ->{
            answer = false;
            window.close();
        });

        HBox bottom = new HBox(10);
        VBox layout = new VBox(10);

        bottom.getChildren().addAll(yesButton, noButton);

        layout.getChildren().addAll(label, bottom);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
