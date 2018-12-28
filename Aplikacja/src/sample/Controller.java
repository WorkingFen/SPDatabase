package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextField loginInput;

    @FXML
    TextField passwordInput;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    private void changeScreen(String fxmlPath, ActionEvent event)throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void loginButtonPushed(ActionEvent event) throws IOException {

        String loginInputText = loginInput.getText();
        String passwordInputText = passwordInput.getText();

        changeScreen("cashier.fxml", event); // do usunięcia
        if (loginInputText.equals("klient") && passwordInputText.equals("iksde")){
            changeScreen("client.fxml", event);
        }
        else if (loginInputText.equals("kasjer") && passwordInputText.equals("xDDD")){
            changeScreen("cashier.fxml", event);
        }
        else{
            System.out.println("Zle haslo");
            //TODO chyba że wywalone
        }

    }
}
