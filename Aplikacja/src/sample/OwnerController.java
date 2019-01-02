package sample;

import JDBC.Employee;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private TableColumn<OwnerIncome, String> objectName;
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

    private ObservableList<OwnerEmployee> getEmployees(Connection conn) throws SQLException {
        int noEmployees;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Pracownicy");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noEmployees = rSet.getInt(1);
        else noEmployees = 0;

        rSet.close();
        stmt.close();

        ObservableList<OwnerEmployee> list = FXCollections.observableArrayList();
        for(int i = 0; i < noEmployees; i++){
            list.add(Employee.getOwnerEmployee(conn, i+1));
        }
        return list;
    }

    /*private ObservableList<OwnerIncome> getIncomes(Connection conn) throws SQLException {
        int noMonths;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Rezerwacje_Toru");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noReservations = rSet.getInt(1);
        else noReservations = 0;

        rSet.close();
        stmt.close();

        ObservableList<CashierPath> list = FXCollections.observableArrayList();
        for(int i = 0; i < noReservations; i++){
            list.add(Reservation.getCashierReservation(conn, "Rezerwuj", i+1));
        }
        return list;
    }*/

    private final ObservableList<OwnerEmployee> employees = getEmployees(Main.jdbc.getConn());

    //private final ObservableList<OwnerIncome> incomes = getIncomes(Main.jdbc.getConn());

    private void initializeEmployees() {
        employeeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeePerk.setCellValueFactory(new PropertyValueFactory<>("perk"));

        employeeTable.getItems().addAll(employees);
    }

    /*private void initializeIncomes() {
        objectName.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        income.setCellValueFactory(new PropertyValueFactory<>("income"));
        expenses.setCellValueFactory(new PropertyValueFactory<>("expenses"));

        incomeTable.getItems().addAll(incomes);
    }*/

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

    private void setItemsEmployeeTable(String poolName) {
        //TODO
    }
    private void setItemsIncomeTable(String poolName) {
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
        setItemsIncomeTable(poolItem);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializePoolList();
        initializeEmployees();
        //initializeIncomes();
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
