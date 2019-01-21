package sample.HR;

import JDBC.Employee;
import JDBC.LoginDetails;
import JDBC.Post;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.Main;
import sample.PopupWindows.PopupWindowAlert;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HRController implements Initializable {

    @FXML
    private TableView<HREmployee> employeeTable;
    @FXML
    private TableColumn<HREmployee, String> employeeID;
    @FXML
    private TableColumn<HREmployee, String> employeeFName;
    @FXML
    private TableColumn<HREmployee, String> employeeLName;
    @FXML
    private TableColumn<HREmployee, String> employeePosition;
    @FXML
    private TableColumn<HREmployee, Button> fireEmployee;
    @FXML
    private TableColumn<HREmployee, ComboBox> changeEmployeePosition;


    @FXML
    private TableView<HRPost> postTable;
    @FXML
    private TableColumn<HRPost, String> positionName;
    @FXML
    private TableColumn<HRPost, String> positionSalary;


    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField positionField;

    public HRController() throws SQLException {
    }

    private ObservableList<HREmployee> getEmployees() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noEmployees;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Pracownicy");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noEmployees = rSet.getInt(1);
        else noEmployees = 0;

        rSet.close();
        stmt.close();

        ObservableList<HREmployee> list = FXCollections.observableArrayList();
        for(int i = 0; i < noEmployees; i++){
            list.add(Employee.getHREmployee(this, conn, i+1));
        }
        return list;
    }

    private ObservableList<HRPost> getPosts() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noPositions;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Stanowiska");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noPositions = rSet.getInt(1);
        else noPositions = 0;

        rSet.close();
        stmt.close();

        ObservableList<HRPost> list = FXCollections.observableArrayList();
        for(int i = 1; i < noPositions; i++){
            list.add(Post.getHRPost(conn, i));
        }
        return list;
    }

    void fireEmployeeInstance(int id) throws  SQLException {
        Employee.deleteEmployee(Main.jdbc.getConn(), id);
        LoginDetails.deleteLoginDetails(Main.jdbc.getConn(), id);
        employeeTable.getItems().clear();
        employees = getEmployees();
        initializeEmployees();
    }

    String[] getPostsNames() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noPositions;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Stanowiska");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noPositions = rSet.getInt(1);
        else noPositions = 0;

        rSet.close();
        stmt.close();

        String[] list = new String[noPositions];
        for(int i = 1; i < noPositions; i++){
            list[i] = Post.getHRPostNames(conn, i);
        }
        return list;
    }

    void changePost(String post, int id) throws SQLException {
        Employee.changeEmployeePost(Main.jdbc.getConn(), post, id);
        employeeTable.getItems().clear();
        employees = getEmployees();
        initializeEmployees();
    }

    private void addEmployee(String name, String surname, String post) throws SQLException {
        Employee.addEmployee(Main.jdbc.getConn(), name, surname, post);
        employeeTable.getItems().clear();
        employees = getEmployees();
        initializeEmployees();

        fNameField.setText(null);
        lNameField.setText(null);
        positionField.setText(null);
    }

    private ObservableList<HREmployee> employees = getEmployees();

    private final ObservableList<HRPost> posts = getPosts();

    private void initializeEmployees() {
        employeeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeFName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeLName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeePosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        fireEmployee.setCellValueFactory(new PropertyValueFactory<>("fireEmployee"));
        changeEmployeePosition.setCellValueFactory(new PropertyValueFactory<>("changePosition"));

        employeeTable.getItems().addAll(employees);
    }

    private void initializePosts() {
        positionName.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        postTable.getItems().addAll(posts);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeEmployees();
        initializePosts();
    }

    public void hireEmployeeButtonPushed(ActionEvent event) throws IOException {
        String fNameInput= fNameField.getText();
        String lNameInput = lNameField.getText();
        String positionInput = positionField.getText();

        if(fNameInput.equals("") || lNameInput.equals("") || positionInput.equals("")) return;

        boolean answer = PopupWindowAlert.display("Czy na pewno chcesz dodać nowego pracownika?","Dodawanie pracownika", 350);
        if(answer){
            try {
                this.addEmployee(fNameInput, lNameInput, positionInput);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void logOutButtonPushed(ActionEvent event)throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
		tableViewScene.getStylesheets().add("ButtonStyles.css");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Logowanie");
        window.setScene(tableViewScene);
        window.show();
    }
}

