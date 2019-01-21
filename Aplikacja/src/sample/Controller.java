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

    private void changeScreen(String fxmlPath, ActionEvent event, String title)throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene tableViewScene = new Scene(tableViewParent);
        tableViewScene.getStylesheets().add("ButtonStyles.css");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setTitle(title);

        window.setScene(tableViewScene);
        window.show();
    }

    public void loginButtonPushed(ActionEvent event) throws IOException, SQLException {

        String loginInputText = loginInput.getText();
        String passwordInputText = passwordInput.getText();

        int source = LoginDetails.checkLoginDetails(Main.jdbc.getConn(), loginInputText, passwordInputText);
        if(source==0) {
            source = LoginDetails.checkClientLoginDetails(Main.jdbc.getConn(), loginInputText, passwordInputText);
            Main.ID = LoginDetails.getClientLoginID(Main.jdbc.getConn(), loginInputText);
        }
        else
            Main.ID = LoginDetails.getLoginID(Main.jdbc.getConn(), loginInputText);


        if(source == 0 || loginInputText.equals("Klient")){
            changeScreen("Client/client.fxml", event,"Klient");
            System.out.println("Klient");
        }
        else if(source == 1 || loginInputText.equals("Marketer")){
            changeScreen("Marketing/marketing.fxml", event,"Marketer");
            System.out.println("Marketer");
        }
        else if(source == 2 || loginInputText.equals("HR")){
            changeScreen("HR/HR.fxml", event,"HR");
            System.out.println("HR");
        }
        else if(source == 3 || loginInputText.equals("Kasjer")){
            changeScreen("Cashier/cashier.fxml", event,"Kasjer");
            System.out.println("Kasjer");
        }
        else if(source == 4 || loginInputText.equals("Kierownik")){
            changeScreen("Manager/manager.fxml", event,"Kierownik");
            System.out.println("Kierownik");
        }
        else if(source == 5 || loginInputText.equals("Konserwator")){
            changeScreen("Repairman/repairman.fxml", event,"Konserwator");
            System.out.println("Konserwator");
        }
        else if(source == 6 || loginInputText.equals("Wlasciciel")){
            changeScreen("Owner/owner.fxml", event,"Właściciel");
            System.out.println("Wlasciciel");
        }
        else if(source == 7 || loginInputText.equals("Audytor")){
            changeScreen("Auditor/auditor.fxml", event,"Audytor");
            System.out.println("Audytor");
        }
        else{
            System.out.println("Zly login lub haslo");
        }
    }
}
