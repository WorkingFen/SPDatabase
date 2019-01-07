package sample.Auditor;

import JDBC.Employee;
import JDBC.Inspection;
import JDBC.Pool;
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
import sample.Main;

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

    private ObservableList<String> getPoolNames() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noPools;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Baseny");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noPools = rSet.getInt(1);
        else noPools = 0;

        rSet.close();
        stmt.close();

        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i = 1; i < noPools; i++){
            String temp = Pool.getPool(conn, i);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<AuditorEmployee> getEmployees(String poolItem) throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noEmployees;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Pracownicy");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noEmployees = rSet.getInt(1);
        else noEmployees = 0;

        rSet.close();
        stmt.close();

        ObservableList<AuditorEmployee> list = FXCollections.observableArrayList();
        for(int i = 0; i < noEmployees; i++){
            AuditorEmployee temp = Employee.getAuditorEmployee(conn, i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<AuditorInspection> getInspections(String poolItem) throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noInspections;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Przeglady");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noInspections = rSet.getInt(1);
        else noInspections = 0;

        rSet.close();
        stmt.close();

        ObservableList<AuditorInspection> list = FXCollections.observableArrayList();
        for(int i = 0; i < noInspections; i++){
            AuditorInspection temp = Inspection.getAuditorInspection(conn, i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<AuditorTransaction> getTransactions(String poolItem) throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noTransactions;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Transakcje");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noTransactions = rSet.getInt(1);
        else noTransactions = 0;

        rSet.close();
        stmt.close();

        ObservableList<AuditorTransaction> list = FXCollections.observableArrayList();
        for(int i = 0; i < noTransactions; i++){
            AuditorTransaction temp = Transaction.getAuditorTransaction(conn, i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<AuditorEmployee> employees = FXCollections.observableArrayList();

    private ObservableList<AuditorInspection> inspections = FXCollections.observableArrayList();

    private ObservableList<AuditorTransaction> transactions = FXCollections.observableArrayList();

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

    private void initializePoolList() throws SQLException {
        ObservableList<String> items = getPoolNames();
        poolList.setItems(items);
    }

    private void changeTables(String poolItem) throws SQLException {
        clearTables();
        employees = getEmployees(poolItem);
        inspections = getInspections(poolItem);
        transactions = getTransactions(poolItem);
        initializeEmployees();
        initializeInspections();
        initializeTransactions();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            initializePoolList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeEmployees();
        initializeInspections();
        initializeTransactions();
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

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}