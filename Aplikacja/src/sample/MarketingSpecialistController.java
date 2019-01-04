package sample;

import JDBC.Pool;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


/*
TabPane ma wyświetlać tabele dla danych basenów:
po kliknięciu w dany basen z listy po lewej,
aplikacja usuwa zawartość tabel i wczytuje nowe elo
 */
public class MarketingSpecialistController implements Initializable {

    @FXML
    private ListView<String> poolList;

    @FXML
    private TableView<MarketingClient> clientsTable;
    @FXML
    private TableColumn<MarketingClient, String> clientID;
    @FXML
    private TableColumn<MarketingClient, String> clientFName;
    @FXML
    private TableColumn<MarketingClient, String> clientLName;
    @FXML
    private TableColumn<MarketingClient, String> clientPosition;

    @FXML
    private TableView<MarketingTopClient> topClientsTable;
    @FXML
    private TableColumn<MarketingTopClient, String> topClientNumber;
    @FXML
    private TableColumn<MarketingTopClient, String> topClientLName;
    @FXML
    private TableColumn<MarketingTopClient, String> topClientFName;
    @FXML
    private TableColumn<MarketingTopClient, String> topClientTransactionsNumber;

    @FXML
    private TableView<MarketingReservation> reservationsTable;
    @FXML
    private TableColumn<MarketingReservation, String> reservationDate;
    @FXML
    private TableColumn<MarketingReservation, String> reservationType;
    @FXML
    private TableColumn<MarketingReservation, String> reservationFName;
    @FXML
    private TableColumn<MarketingReservation, String> reservationLName;

    @FXML
    private TableView<MarketingTransaction> transactionsTable;
    @FXML
    private TableColumn<MarketingTransaction, String> transactionDate;
    @FXML
    private TableColumn<MarketingTransaction, String> transactionValue;
    @FXML
    private TableColumn<MarketingTransaction, String> transactionFName;
    @FXML
    private TableColumn<MarketingTransaction, String> transactionLName;

    @FXML
    private Text usedReservationsText;

    private void clearTables(){
        clientsTable.getItems().clear();
        topClientsTable.getItems().clear();
        reservationsTable.getItems().clear();
        transactionsTable.getItems().clear();
    }

    private ObservableList<String> getPoolNames(Connection conn) throws SQLException {
        int noPools;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Baseny");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noPools = rSet.getInt(1);
        else noPools = 0;

        rSet.close();
        stmt.close();

        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i = 0; i < noPools; i++){
            String temp = Pool.getPool(conn, i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    /*private ObservableList<ClientPath> getReservations(Connection conn, String poolItem) throws SQLException {
        int minReservation;
        int maxReservation;
        PreparedStatement stmt = conn.prepareStatement("SELECT MIN(Numer_Rezerwacji) FROM Rezerwacje_Toru WHERE Data_I_Godzina > SYSDATE");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) minReservation = rSet.getInt(1);
        else minReservation = 0;

        rSet.close();
        stmt.close();

        stmt = conn.prepareStatement("SELECT MAX(Numer_Rezerwacji) FROM Rezerwacje_Toru WHERE Data_I_Godzina > SYSDATE");
        rSet = stmt.executeQuery();

        if(rSet.next()) maxReservation = rSet.getInt(1);
        else maxReservation = 0;

        rSet.close();
        stmt.close();

        ObservableList<ClientPath> list = FXCollections.observableArrayList();
        for(int i = minReservation-1; i < maxReservation; i++){
            ClientPath temp = Reservation.getClientReservation(conn, "Zarezerwuj", i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<ClientLesson> lessons = FXCollections.observableArrayList();

    private void initializeLessons(){
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonEnrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        lessonRescuer.setCellValueFactory(new PropertyValueFactory<>("rescuer"));
        reserveLesson.setCellValueFactory(new PropertyValueFactory<>("enrollButton"));

        lessonTable.getItems().addAll(lessons);
    }*/

    private void initializePoolList() throws SQLException {
        ObservableList<String> items = getPoolNames(Main.jdbc.getConn());
        poolList.setItems(items);
    }

    private void initializeUsedReservations(){
        usedReservationsText.setText("0.005%");
    }

    private void changeTables(String poolItem) throws SQLException {
        clearTables();
        //lessons = getLessons(Main.jdbc.getConn(), poolItem);
        //clientPaths = getReservations(Main.jdbc.getConn(), poolItem);
        //initializeLessons();
        //initializePaths();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializePoolList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //initializeWorkerTable();
        //initializeInspectionTable();
        initializeUsedReservations();
    }


    @FXML
    public void changeReservationsPeriod(){

    }
    @FXML
    public void changeTransactionsPeriod(){

    }
    @FXML
    public void poolItemClicked() throws SQLException {

        String poolItem = poolList.getSelectionModel().getSelectedItem();

        if(poolItem == null) return;

        String[] poolName = poolItem.split(",");
        System.out.println("clicked on " + poolName[0]);   //debug

        changeTables(poolName[0]);

    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
