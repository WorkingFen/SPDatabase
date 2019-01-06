package sample.Owner;

import JDBC.Employee;
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

public class OwnerController implements Initializable {

    @FXML
    private ListView<String> poolList;

    @FXML
    private TableView<OwnerEmployee> employeeTable;
    @FXML
    private TableColumn<OwnerEmployee, String> employeeID;
    @FXML
    private TableColumn<OwnerEmployee, String> employeeName;
    @FXML
    private TableColumn<OwnerEmployee, String> employeeSurname;
    @FXML
    private TableColumn<OwnerEmployee, String> employeePerk;


    @FXML
    private TableView<OwnerIncome> incomeTable;
    @FXML
    private TableColumn<OwnerIncome, String> date;
    @FXML
    private TableColumn<OwnerIncome, String> income;
    @FXML
    private TableColumn<OwnerIncome, String> expenses;

    public OwnerController() throws SQLException {
    }

    private void clearTables(){
        employeeTable.getItems().clear();
        incomeTable.getItems().clear();
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
        for(int i = 0; i < noPools; i++){
            String temp = Pool.getPool(conn, i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<OwnerEmployee> getEmployees(String poolItem) throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noEmployees;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Pracownicy");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noEmployees = rSet.getInt(1);
        else noEmployees = 0;

        rSet.close();
        stmt.close();

        ObservableList<OwnerEmployee> list = FXCollections.observableArrayList();
        for(int i = 0; i < noEmployees; i++){
            OwnerEmployee temp = Employee.getOwnerEmployee(conn, i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<OwnerIncome> getIncomes(String poolItem) throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int minYear;
        int maxYear;
        PreparedStatement stmt = conn.prepareStatement("SELECT MIN(to_char(Data, 'YYYY')) FROM Transakcje");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) minYear = rSet.getInt(1);
        else minYear = 1970;

        rSet.close();
        stmt.close();

        stmt = conn.prepareStatement("SELECT MAX(to_char(Data, 'YYYY')) FROM Transakcje");
        rSet = stmt.executeQuery();

        if(rSet.next()) maxYear = rSet.getInt(1);
        else maxYear = 2037;

        rSet.close();
        stmt.close();

        ObservableList<OwnerIncome> list = FXCollections.observableArrayList();
        for(int year = minYear-1; year < maxYear; year++){
            for(int month = 0; month < 12; month++){
                OwnerIncome temp = Transaction.getOwnerIncome(conn, Integer.toString(year+1), month+1, poolItem);
                if(temp != null) list.add(temp);
            }
        }
        return list;
    }

    private ObservableList<OwnerEmployee> employees = FXCollections.observableArrayList();

    private ObservableList<OwnerIncome> incomes = FXCollections.observableArrayList();

    private void initializeEmployees() {
        employeeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeePerk.setCellValueFactory(new PropertyValueFactory<>("perk"));

        employeeTable.getItems().addAll(employees);
    }

    private void initializeIncomes() {
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        income.setCellValueFactory(new PropertyValueFactory<>("income"));
        expenses.setCellValueFactory(new PropertyValueFactory<>("expenses"));

        incomeTable.getItems().addAll(incomes);
    }

    private void initializePoolList() throws SQLException {
        ObservableList<String> items = getPoolNames();
        poolList.setItems(items);
    }

    private void changeTables(String poolItem) throws SQLException {
        clearTables();
        employees = getEmployees(poolItem);
        incomes = getIncomes(poolItem);
        initializeEmployees();
        initializeIncomes();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            initializePoolList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeEmployees();
        initializeIncomes();
    }

    @FXML
    public void poolItemClicked() throws SQLException {

        String poolItem = poolList.getSelectionModel().getSelectedItem();

        if(poolItem == null) return;

        String[] poolName = poolItem.split(",");
        System.out.println("clicked on " + poolItem);   //debug

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
