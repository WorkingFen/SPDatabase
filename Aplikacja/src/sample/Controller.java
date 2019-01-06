package sample;

import JDBC.LoginDetails;
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
import java.sql.SQLException;
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

    public void loginButtonPushed(ActionEvent event) throws IOException, SQLException {

        String loginInputText = loginInput.getText();
        String passwordInputText = passwordInput.getText();

        int source = LoginDetails.checkLoginDetails(Main.jdbc.getConn(), loginInputText, passwordInputText);

        if(loginInputText.equals("Klient")){
            changeScreen("client.fxml", event);
            System.out.println("Klient");
        }
        else if(source == 1 || loginInputText.equals("Marketer")){
            changeScreen("marketing.fxml", event);
            System.out.println("Marketer");
        }
        else if(source == 2 || loginInputText.equals("HR")){
            changeScreen("HR.fxml", event);
            System.out.println("HR");
        }
        else if(source == 3 || loginInputText.equals("Kasjer")){
            changeScreen("cashier.fxml", event);
            System.out.println("Kasjer");
        }
        else if(source == 4 || loginInputText.equals("Kierownik")){
            changeScreen("manager.fxml", event);
            System.out.println("Kierownik");
        }
        else if(source == 5 || loginInputText.equals("Konserwator")){
            changeScreen("repairman.fxml", event);
            System.out.println("Konserwator");
        }
        else if(source == 6 || loginInputText.equals("Wlasciciel")){
            changeScreen("owner.fxml", event);
            System.out.println("Wlasciciel");
        }
        else if(source == 7 || loginInputText.equals("Audytor")){
            changeScreen("auditor.fxml", event);
            System.out.println("Audytor");
        }
        else{
            System.out.println("Zly login lub haslo");
        }
    }
}
