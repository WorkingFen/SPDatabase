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

        //changeScreen("owner.fxml", event); // do usunięcia
        if (loginInputText.equals("1") && passwordInputText.equals("")){
            changeScreen("client.fxml", event);
        }
        else if (loginInputText.equals("2") && passwordInputText.equals("")){
            changeScreen("cashier.fxml", event);
        }
        else if (loginInputText.equals("3") && passwordInputText.equals("")){
            changeScreen("HR.fxml", event);
        }
        else if (loginInputText.equals("4") && passwordInputText.equals("")){
            changeScreen("owner.fxml", event);
        }
        else if (loginInputText.equals("5") && passwordInputText.equals("")){
            changeScreen("auditor.fxml", event);
        }
        else{
            System.out.println("Zle haslo");
            //TODO chyba że wywalone
        }

    }
}
