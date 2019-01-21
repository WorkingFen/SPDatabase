package sample.Manager;

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

/*
TabPane ma wyświetlać tabele dla danych basenów:
po kliknięciu w dany basen z listy po lewej,
aplikacja usuwa zawartość tabel i wczytuje nowe elo
 */
public class ManagerController implements Initializable {

    //TODO pensje transakcje zyski

    @FXML
    private TableView<ManagerEmployee> employeeTable;
    @FXML
    private TableColumn<ManagerEmployee, String> employeeID;
    @FXML
    private TableColumn<ManagerEmployee, String> employeeFName;
    @FXML
    private TableColumn<ManagerEmployee, String> employeeLName;
    @FXML
    private TableColumn<ManagerEmployee, String> employeePosition;

    @FXML
    private TableView<ManagerTransaction> transactionTable;
    @FXML
    private TableColumn<ManagerTransaction, String> transactionNumber;
    @FXML
    private TableColumn<ManagerTransaction, String> transactionDate;
    @FXML
    private TableColumn<ManagerTransaction, String> transactionPrice;

    @FXML
    private TableView<ManagerIncome> incomeTable;
    @FXML
    private TableColumn<ManagerIncome, String> incomeMonth;
    @FXML
    private TableColumn<ManagerIncome, String> income;

    @FXML
    private TableView<ManagerSalary> salaryTable;
    @FXML
    private TableColumn<ManagerSalary, String> salaryMonth;
    @FXML
    private TableColumn<ManagerSalary, String> salary;

    @FXML
    private TableView<ManagerInspection>  inspectionsTable;
    @FXML
    private TableColumn<ManagerInspection, String> inspectionNumber;
    @FXML
    private TableColumn<ManagerInspection, String> inspectionDate;
    @FXML
    private TableColumn<ManagerInspection, String> inspectionComment;

    public ManagerController() throws SQLException {
    }

    private ObservableList<ManagerEmployee> getEmployees() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noEmployees;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Pracownicy");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noEmployees = rSet.getInt(1);
        else noEmployees = 0;

        rSet.close();
        stmt.close();

        ObservableList<ManagerEmployee> list = FXCollections.observableArrayList();
        for(int i = 0; i < noEmployees; i++){
            list.add(Employee.getManagerEmployee(conn, i+1));
        }
        return list;
    }

    private ObservableList<ManagerTransaction> getTransactions() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noTransactions;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Transakcje");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noTransactions = rSet.getInt(1);
        else noTransactions = 0;

        rSet.close();
        stmt.close();

        ObservableList<ManagerTransaction> list = FXCollections.observableArrayList();
        for(int i = 0; i < noTransactions; i++){
            list.add(Transaction.getManagerTransaction(conn, i+1));
        }
        return list;
    }

    private ObservableList<ManagerIncome> getIncomes() throws SQLException {
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

        ObservableList<ManagerIncome> list = FXCollections.observableArrayList();
        for(int year = minYear-1; year < maxYear; year++){
            for(int month = 0; month < 12; month++){
                ManagerIncome temp = Transaction.getManagerIncome(conn, Integer.toString(year+1), month+1);
                if(temp != null) list.add(temp);
            }
        }
        return list;
    }

    private ObservableList<ManagerSalary> getSalaries() throws SQLException {
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

        ObservableList<ManagerSalary> list = FXCollections.observableArrayList();
        for(int year = minYear-1; year < maxYear; year++){
            for(int month = 0; month < 12; month++){
                ManagerSalary temp = Transaction.getManagerSalary(conn, Integer.toString(year+1), month+1);
                if(temp != null) list.add(temp);
            }
        }
        return list;
    }

    private ObservableList<ManagerInspection> getInspections() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noInspections;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Przeglady");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noInspections = rSet.getInt(1);
        else noInspections = 0;

        rSet.close();
        stmt.close();

        ObservableList<ManagerInspection> list = FXCollections.observableArrayList();
        for(int i = 0; i < noInspections; i++){
            list.add(Inspection.getManagerInspection(conn, i+1));
        }
        return list;
    }

    private final ObservableList<ManagerEmployee> employees = getEmployees();

    private final ObservableList<ManagerTransaction> transactions = getTransactions();

    private final ObservableList<ManagerIncome> incomes = getIncomes();

    private final ObservableList<ManagerSalary> salaries = getSalaries();

    private final ObservableList<ManagerInspection> inspections = getInspections();

    private void initializeEmployee()
    {
        employeeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeFName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeLName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeePosition.setCellValueFactory(new PropertyValueFactory<>("post"));

        employeeTable.getItems().addAll(employees);
    }

    private void initializeTransaction()
    {
        transactionNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        transactionPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        transactionTable.getItems().addAll(transactions);
    }

    private void initializeIncome()
    {
        incomeMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        income.setCellValueFactory(new PropertyValueFactory<>("income"));

        incomeTable.getItems().addAll(incomes);
    }

    private void initializeSalary()
    {
        salaryMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salaryTable.getItems().addAll(salaries);
    }

    private void initializeInspection()
    {
        inspectionNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        inspectionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        inspectionComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        inspectionsTable.getItems().addAll(inspections);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEmployee();
        initializeTransaction();
        initializeIncome();
        initializeSalary();
        initializeInspection();
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setTitle("Logowanie");
        window.setScene(tableViewScene);
        window.show();
    }
}
