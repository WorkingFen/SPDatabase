package sample;

import JDBC.Employee;
import JDBC.Inspection;
import JDBC.Transaction;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AuditorController implements Initializable {

    @FXML
    private ListView<String> poolList;

    @FXML
    private TableView<AuditorEmployee> employeeTable;
    @FXML
    private TableColumn<AuditorEmployee, String> employeeID;
    @FXML
    private TableColumn<AuditorEmployee, String> employeeName;
    @FXML
    private TableColumn<AuditorEmployee, String> employeeSurname;
    @FXML
    private TableColumn<AuditorEmployee, String> employeePost;
    @FXML
    private TableColumn<AuditorEmployee, String> employeePerk;


    @FXML
    private TableView<AuditorInspection> inspectionTable;
    @FXML
    private TableColumn<AuditorInspection, String> inspectionID;
    @FXML
    private TableColumn<AuditorInspection, String> inspectionObject;
    @FXML
    private TableColumn<AuditorInspection, String> inspectionDate;
    @FXML
    private TableColumn<AuditorInspection, String> comment;


    @FXML
    private TableView<AuditorTransaction> transactionTable;
    @FXML
    private TableColumn<AuditorTransaction, String> transactionID;
    @FXML
    private TableColumn<AuditorTransaction, String> transactionDate;
    @FXML
    private TableColumn<AuditorTransaction, String> transactionService;
    @FXML
    private TableColumn<AuditorTransaction, String> transactionPrice;

    public AuditorController() throws SQLException {
    }

    private void clearTables(){
        employeeTable.getItems().clear();
        inspectionTable.getItems().clear();
        transactionTable.getItems().clear();
    }

    private ObservableList<AuditorEmployee> getEmployees(Connection conn) throws SQLException {
        int noEmployees;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Pracownicy");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noEmployees = rSet.getInt(1);
        else noEmployees = 0;

        rSet.close();
        stmt.close();

        ObservableList<AuditorEmployee> list = FXCollections.observableArrayList();
        for(int i = 0; i < noEmployees; i++){
            list.add(Employee.getAuditorEmployee(conn, i+1));
        }
        return list;
    }

    private ObservableList<AuditorInspection> getInspections(Connection conn) throws SQLException {
        int noInspections;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Przeglady");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noInspections = rSet.getInt(1);
        else noInspections = 0;

        rSet.close();
        stmt.close();

        ObservableList<AuditorInspection> list = FXCollections.observableArrayList();
        for(int i = 0; i < noInspections; i++){
            list.add(Inspection.getAuditorInspection(conn, i+1));
        }
        return list;
    }

    private ObservableList<AuditorTransaction> getTransactions(Connection conn) throws SQLException {
        int noTransactions;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Transakcje");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noTransactions = rSet.getInt(1);
        else noTransactions = 0;

        rSet.close();
        stmt.close();

        ObservableList<AuditorTransaction> list = FXCollections.observableArrayList();
        for(int i = 0; i < noTransactions; i++){
            list.add(Transaction.getAuditorTransaction(conn, i+1));
        }
        return list;
    }

    private final ObservableList<AuditorEmployee> employees = getEmployees(Main.jdbc.getConn());

    private final ObservableList<AuditorInspection> inspections = getInspections(Main.jdbc.getConn());

    private final ObservableList<AuditorTransaction> transactions = getTransactions(Main.jdbc.getConn());

    private void initializeEmployees() {
        employeeID.setCellValueFactory(new PropertyValueFactory<>("number"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeePost.setCellValueFactory(new PropertyValueFactory<>("post"));
        employeePerk.setCellValueFactory(new PropertyValueFactory<>("perk"));

        employeeTable.getItems().addAll(employees);
    }

    private void initializeInspections() {
        inspectionID.setCellValueFactory(new PropertyValueFactory<>("number"));
        inspectionObject.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        inspectionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        inspectionTable.getItems().addAll(inspections);
    }

    private void initializeTransactions() {
        transactionID.setCellValueFactory(new PropertyValueFactory<>("number"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        transactionService.setCellValueFactory(new PropertyValueFactory<>("service"));
        transactionPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        transactionTable.getItems().addAll(transactions);
    }

    private void initializePoolList(){
        //TODO

        //przykładowe
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        poolList.setItems(items);
    }

    private void changeTables()
    {
        clearTables();

        //TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializePoolList();
        initializeEmployees();
        initializeInspections();
        initializeTransactions();
    }

    private void setItemsEmployeeTable(String poolName) {
        //TODO
    }
    private void setItemsInspectionTable(String poolName) {
        //TODO
    }
    private void setItemsTransactionTable(String poolName) {
        //TODO
    }

    @FXML
    public void poolItemClicked() {

        String poolItem = poolList.getSelectionModel().getSelectedItem();

        System.out.println("clicked on " + poolItem);   //debug

        if(poolItem == null) return;

        // set new items
        clearTables();
        setItemsEmployeeTable(poolItem);
        setItemsInspectionTable(poolItem);
        setItemsTransactionTable(poolItem);
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}