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
import sample.Cashier.CashierController;
import sample.Client.ClientController;
import sample.HR.HRController;
import sample.Manager.ManagerController;
import sample.Repairman.RepairmanController;

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

    private FXMLLoader changeScreen(String fxmlPath, ActionEvent event, String title)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        tableViewScene.getStylesheets().add("ButtonStyles.css");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setTitle(title);

        window.setScene(tableViewScene);
        window.show();
        return loader;
    }

    public void loginButtonPushed(ActionEvent event) throws IOException, SQLException {

        String loginInputText = loginInput.getText();
        String passwordInputText = passwordInput.getText();

        int ID;

        int source = LoginDetails.checkLoginDetails(Main.jdbc.getConn(), loginInputText, passwordInputText);
        if(source==0) {
            source = LoginDetails.checkClientLoginDetails(Main.jdbc.getConn(), loginInputText, passwordInputText);
            ID = LoginDetails.getClientLoginID(Main.jdbc.getConn(), loginInputText);
        }
        else if(source!=6 && source!=7)
            ID = LoginDetails.getLoginID(Main.jdbc.getConn(), loginInputText);
        else ID = 0;


        if(source == 0 || loginInputText.equals("Klient")){
            FXMLLoader loader = changeScreen("Client/client.fxml", event,"Klient");
            ClientController cc = loader.getController();
            cc.setID(ID);
            System.out.println("Klient");
        }
        else if(source == 1 || loginInputText.equals("Marketer")){
            changeScreen("Marketing/marketing.fxml", event,"Marketer");
            System.out.println("Marketer");
        }
        else if(source == 2 || loginInputText.equals("HR")){
            FXMLLoader loader = changeScreen("HR/HR.fxml", event,"HR");
            HRController hrc = loader.getController();
            hrc.setPoolNo(ID);
            System.out.println("HR");
        }
        else if(source == 3 || loginInputText.equals("Kasjer")){
            FXMLLoader loader = changeScreen("Cashier/cashier.fxml", event,"Kasjer");
            CashierController cc = loader.getController();
            cc.setPoolNo(ID);
            System.out.println("Kasjer");
        }
        else if(source == 4 || loginInputText.equals("Kierownik")){
            FXMLLoader loader = changeScreen("Manager/manager.fxml", event,"Kierownik");
            ManagerController mc = loader.getController();
            mc.setPoolNo(ID);
            System.out.println("Kierownik");
        }
        else if(source == 5 || loginInputText.equals("Konserwator")){
            FXMLLoader loader = changeScreen("Repairman/repairman.fxml", event,"Konserwator");
            RepairmanController rc = loader.getController();
            rc.setPoolNo(ID);
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
