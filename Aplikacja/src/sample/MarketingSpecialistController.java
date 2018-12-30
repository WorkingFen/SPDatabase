package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/*
TabPane ma wyświetlać tabele dla danych basenów:
po kliknięciu w dany basen z listy po lewej,
aplikacja usuwa zawartość tabel i wczytuje nowe elo
 */
public class MarketingSpecialistController implements Initializable {

    //TODO pensje transakcje zyski

    @FXML
    private TableView clientsTable;
    @FXML
    private TableView reservationsTable;

    @FXML
    private ListView<String> poolList;

    @FXML
    private Text usedReservationsText;

    private void clearTables(){
        //clientsTable.getItems().clear();
        //sreservationsTable.getItems().clear();
    }

    private void initializeWorkerTable()
    {
        //TODO
    }

    private void initializeInspectionTable()
    {
        //TODO
    }

    private void initializePoolList(){
        //TODO

        //przykładowe
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        poolList.setItems(items);
    }

    private void initializeUsedReservations(){
        usedReservationsText.setText("0.005%");
    }

    private void changeTables()
    {
        clearTables();

        //TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePoolList();
        initializeWorkerTable();
        initializeInspectionTable();
        initializeUsedReservations();
    }

    private void setItemsInspectionTable(String poolName) {
        //TODO
    }
    private void setItemsWorkerTable(String poolName) {
        //TODO
    }
    @FXML
    public void changeReservationsPeriod(){

    }
    @FXML
    public void changeTransactionsPeriod(){

    }
    @FXML
    public void poolItemClicked() {

        String poolItem = poolList.getSelectionModel().getSelectedItem();

        System.out.println("clicked on " + poolItem);   //debug

        if(poolItem == null) return;

        // set new items
        clearTables();
        setItemsInspectionTable(poolItem);
        setItemsWorkerTable(poolItem);

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
