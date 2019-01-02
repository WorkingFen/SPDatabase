package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*
TabPane ma wyświetlać tabele dla danych basenów:
po kliknięciu w dany basen z listy po lewej,
aplikacja usuwa zawartość tabel i wczytuje nowe elo
 */
public class ManagerController implements Initializable {

    //TODO pensje transakcje zyski

    @FXML
    private TableView workersTable;
    @FXML
    private TableColumn workerID;
    @FXML
    private TableColumn workerFName;
    @FXML
    private TableColumn workerLName;
    @FXML
    private TableColumn workerPosition;

    @FXML
    private TableView  inspectionsTable;
    @FXML
    private TableColumn inspectionNumber;
    @FXML
    private TableColumn inspectionDate;
    @FXML
    private TableColumn inspectionDescription;


    private void initializeWorkerTable()
    {
        //TODO
    }

    private void initializeInspectionTable()
    {
        //TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWorkerTable();
        initializeInspectionTable();
    }

    // powrót do okna logowania
    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
