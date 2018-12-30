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

        if(source == 1){
            changeScreen("auditor.fxml", event);
            System.out.println("Audytor");
        }
        else if(source == 2){
            changeScreen("owner.fxml", event);
            System.out.println("Wlasciciel");
        }
        else if(source == 4){
            changeScreen("HR.fxml", event);
            System.out.println("HR");
        }
        else if(source == 5){
            changeScreen("cashier.fxml", event);
            System.out.println("Kasjer");
        }
        else if(source == 9){
            changeScreen("client.fxml", event);
        }
        else{
            System.out.println("Zle haslo");
            //TODO chyba że wywalone
        }

        //changeScreen("owner.fxml", event); // do usunięcia
        /*
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
        else if (loginInputText.equals("6") && passwordInputText.equals("")){
            changeScreen("manager.fxml", event);
        }
        else if (loginInputText.equals("7") && passwordInputText.equals("")){
            changeScreen("marketingSpecialist.fxml", event);
        }
        else{
            System.out.println("Zle haslo");
            //TODO chyba że wywalone
        }*/

    }
}
