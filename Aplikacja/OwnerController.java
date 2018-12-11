package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ListView listView;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            changeWorkersButtonPushed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeWorkersButtonPushed() throws IOException {
        TableView loader = FXMLLoader.load(getClass().getResource("ownerWorkers.fxml"));
        borderPane.setCenter(loader);
    }

    public void changeIncomeAndCostsButtonPushed() throws IOException {
        TableView loader = FXMLLoader.load(getClass().getResource("ownerIncomeAndCosts.fxml"));
        borderPane.setCenter(loader);
    }

    public void changeLogOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
