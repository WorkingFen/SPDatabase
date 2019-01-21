package sample.Cashier;

import JDBC.Attendee;
import JDBC.Client;
import JDBC.Lesson;
import JDBC.Reservation;
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

public class CashierController implements Initializable {

    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;


    @FXML
    private TableView<CashierClient> clientTable;
    @FXML
    private TableColumn<CashierClient, String> clientNumber;
    @FXML
    private TableColumn<CashierClient, String> clientFName;
    @FXML
    private TableColumn<CashierClient, String> clientLName;
    @FXML
    private TableColumn<CashierClient, String> clientPhone;
    @FXML
    private TableColumn<CashierClient, String> clientEMail;
    @FXML
    private TableColumn<CashierClient, String> editClient;
    @FXML
    private TableColumn<CashierClient, String> deleteClient;


    @FXML
    private TableView<CashierLesson> lessonTable;
    @FXML
    private TableColumn<CashierLesson, String> lessonDate;
    @FXML
    private TableColumn<CashierLesson, String> lessonEnrolled;
    @FXML
    private TableColumn<CashierLesson, String> lessonRescuer;
    @FXML
    private TableColumn<CashierLesson, String> reserveLesson;
    @FXML
    private TableColumn<CashierLesson, String> dismissLesson;


    @FXML
    private TableView<CashierPath> pathTable;
    @FXML
    private TableColumn<CashierPath, String> pathReservationNumber;
    @FXML
    private TableColumn<CashierPath, String> pathHours;
    @FXML
    private TableColumn<CashierPath, String> pathNumber;
    @FXML
    private TableColumn<CashierPath, String> pathState;
    @FXML
    private TableColumn<CashierPath, String> reservePath;
    @FXML
    private TableColumn<CashierPath, String> cancelReservation;

    public CashierController() throws SQLException {
    }

    private ObservableList<CashierLesson> getLessons() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int minLesson;
        int maxLesson;
        PreparedStatement stmt = conn.prepareStatement("SELECT MIN(Numer_Lekcji) FROM Lekcje_Plywania WHERE Data_I_Godzina > SYSDATE");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) minLesson = rSet.getInt(1);
        else minLesson = 0;

        rSet.close();
        stmt.close();

        stmt = conn.prepareStatement("SELECT MAX(Numer_Lekcji) FROM Lekcje_Plywania WHERE Data_I_Godzina > SYSDATE");
        rSet = stmt.executeQuery();

        if(rSet.next()) maxLesson = rSet.getInt(1);
        else maxLesson = 0;

        rSet.close();
        stmt.close();

        ObservableList<CashierLesson> list = FXCollections.observableArrayList();
        for(int i = minLesson-1; i < maxLesson; i++){
            CashierLesson temp = Lesson.getCashierLesson(this, conn, "Zapisz", "Wypisz", i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<CashierPath> getReservations() throws SQLException {
        Connection conn = Main.jdbc.getConn();
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

        ObservableList<CashierPath> list = FXCollections.observableArrayList();
        for(int i = minReservation-1; i < maxReservation; i++){
            CashierPath temp = Reservation.getCashierReservation(this, conn, "Rezerwuj", "Anuluj", i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<CashierClient> getClients() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int noClients;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Klienci");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noClients = rSet.getInt(1);
        else noClients = 0;

        rSet.close();
        stmt.close();

        ObservableList<CashierClient> list = FXCollections.observableArrayList();
        for(int i = 0; i < noClients; i++){
            CashierClient temp = Client.getCashierClient(this, conn, i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    void deleteClientInstance(String name, String surname, String email) throws SQLException {
        Client.deleteClient(Main.jdbc.getConn(), name, surname, email);
        clientTable.getItems().clear();
        clients = getClients();
        initializeClients();
    }

    void editClientInstance(int number, String name, String surname, String phone, String email) throws SQLException {
        Client.editClient(Main.jdbc.getConn(), number, name, surname, phone, email);
        clientTable.getItems().clear();
        clients = getClients();
        initializeClients();
    }

    int addNewClient(String name, String surname, String phone, String email) throws SQLException {
        int clientID = Client.addClient(Main.jdbc.getConn(), name, surname, phone, email);
        clientTable.getItems().clear();
        clients = getClients();
        initializeClients();

        return clientID;
    }

    int getClientInstance(String name, String surname, String phone) throws SQLException {
        return Client.getClient(Main.jdbc.getConn(), name, surname, phone);
    }

    void addAttendee(int id, int clientID) throws SQLException {
        Attendee.addAttendee(Main.jdbc.getConn(), id, clientID);
        lessonTable.getItems().clear();
        lessons = getLessons();
        initializeLessons();
    }

    void deleteAttendee(int id, int clientID) throws  SQLException {
        Attendee.deleteAttendee(Main.jdbc.getConn(), id, clientID);
        lessonTable.getItems().clear();
        lessons = getLessons();
        initializeLessons();
    }

    void addReservation(int id, int clientID) throws SQLException {
        Reservation.addCashierReservation(Main.jdbc.getConn(), id, clientID);
        pathTable.getItems().clear();
        paths = getReservations();
        initializePaths();
    }

    void deleteReservation(int id, int clientID) throws SQLException {
        Reservation.deleteCashierReservation(Main.jdbc.getConn(), id, clientID);
        pathTable.getItems().clear();
        paths = getReservations();
        initializePaths();
    }

    private void addClient(String name, String surname, String phone, String email) throws SQLException {
        Client.addClient(Main.jdbc.getConn(), name, surname, phone, email);
        clientTable.getItems().clear();
        clients = getClients();
        initializeClients();

        fNameField.setText(null);
        lNameField.setText(null);
        phoneNumberField.setText(null);
        emailField.setText(null);
    }

    private ObservableList<CashierLesson> lessons = getLessons();

    private ObservableList<CashierPath> paths = getReservations();

    private ObservableList<CashierClient> clients = getClients();

    private void initializeLessons() {
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonEnrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        lessonRescuer.setCellValueFactory(new PropertyValueFactory<>("rescuer"));
        reserveLesson.setCellValueFactory(new PropertyValueFactory<>("enrollButton"));
        dismissLesson.setCellValueFactory(new PropertyValueFactory<>("dismissButton"));

        lessonTable.getItems().addAll(lessons);
    }

    private void initializePaths() {
        pathReservationNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        pathHours.setCellValueFactory(new PropertyValueFactory<>("date"));
        pathNumber.setCellValueFactory(new PropertyValueFactory<>("pathNumber"));
        pathState.setCellValueFactory(new PropertyValueFactory<>("state"));
        reservePath.setCellValueFactory(new PropertyValueFactory<>("reserveButton"));
        cancelReservation.setCellValueFactory(new PropertyValueFactory<>("cancelButton"));

        pathTable.getItems().addAll(paths);
    }

    private void initializeClients() {
        clientNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        clientFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clientEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        editClient.setCellValueFactory(new PropertyValueFactory<>("editButton"));
        deleteClient.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        clientTable.getItems().addAll(clients);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeLessons();
        initializePaths();
        initializeClients();

    }

    public void addClientButtonPushed(ActionEvent event) throws IOException {

        String fNameInput= fNameField.getText();
        String lNameInput = lNameField.getText();
        String phoneNumberInput = phoneNumberField.getText();
        String emailInput = emailField.getText();

        if(fNameInput.equals("") || lNameInput.equals("") || phoneNumberInput.equals("")) return;

        boolean answer = PopupWindowAlert.display("Czy na pewno chcesz dodać nowego klienta?","Dodawanie klienta", 350);
        if(answer){
            try {
                this.addClient(fNameInput, lNameInput, phoneNumberInput, emailInput);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void logOutButtonPushed(ActionEvent event)throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Logowanie");

        window.setScene(tableViewScene);
        window.show();
    }
}

