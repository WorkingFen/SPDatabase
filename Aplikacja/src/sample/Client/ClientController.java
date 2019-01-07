package sample.Client;

import JDBC.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class ClientController implements Initializable {

    @FXML
    private ListView<String> poolList;

    @FXML
    private TableView<ClientPath> pathTable;
    @FXML
    private TableColumn<ClientPath, String> number;
    @FXML
    private TableColumn<ClientPath, String> pathHours;
    @FXML
    private TableColumn<ClientPath, String> pathNumber;
    @FXML
    private TableColumn<ClientPath, Button> reservePath;

    @FXML
    private TableView<ClientLesson> lessonTable;
    @FXML
    private TableColumn<ClientLesson, String> lessonDate;
    @FXML
    private TableColumn<ClientLesson, String> lessonEnrolled;
    @FXML
    private TableColumn<ClientLesson, String> lessonRescuer;
    @FXML
    private TableColumn<ClientLesson, Button> reserveLesson;

    private String[] poolName;

    public ClientController() throws SQLException {
    }

    private void clearTables(){
        pathTable.getItems().clear();
        lessonTable.getItems().clear();
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

    private ObservableList<ClientLesson> getLessons(String poolItem) throws SQLException {
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

        ObservableList<ClientLesson> list = FXCollections.observableArrayList();
        for(int i = minLesson-1; i < maxLesson; i++){
            ClientLesson temp = Lesson.getClientLesson(this, conn, "Zapisz się", i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<ClientPath> getReservations(String poolItem) throws SQLException {
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

        ObservableList<ClientPath> list = FXCollections.observableArrayList();
        for(int i = minReservation-1; i < maxReservation; i++){
            ClientPath temp = Reservation.getClientReservation(this, conn, "Zarezerwuj", i+1, poolItem);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    int addNewClient(String name, String surname, String phone, String email) throws SQLException {
        return Client.addClient(Main.jdbc.getConn(), name, surname, phone, email);
    }

    int getClientInstance(String name, String surname, String phone) throws SQLException {
        return Client.getClient(Main.jdbc.getConn(), name, surname, phone);
    }

    void addReservation(int id, int clientID) throws SQLException {
        Reservation.addClientReservation(Main.jdbc.getConn(), id, clientID);
        pathTable.getItems().clear();
        clientPaths = getReservations(poolName[0]);
        initializePaths();
    }

    void addAttendee(int id, int clientID) throws SQLException {
        Attendee.addAttendee(Main.jdbc.getConn(), id, clientID);
        lessonTable.getItems().clear();
        lessons = getLessons(poolName[0]);
        initializeLessons();
    }

    private ObservableList<ClientLesson> lessons = FXCollections.observableArrayList();

    private ObservableList<ClientPath> clientPaths = FXCollections.observableArrayList();

    private void initializeLessons(){
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonEnrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        lessonRescuer.setCellValueFactory(new PropertyValueFactory<>("rescuer"));
        reserveLesson.setCellValueFactory(new PropertyValueFactory<>("enrollButton"));

        lessonTable.getItems().addAll(lessons);
    }

    private void initializePaths(){
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        pathHours.setCellValueFactory(new PropertyValueFactory<>("date"));
        pathNumber.setCellValueFactory(new PropertyValueFactory<>("pathNumber"));
        reservePath.setCellValueFactory(new PropertyValueFactory<>("reserveButton"));

        pathTable.getItems().addAll(clientPaths);
    }

    private void initializePoolList() throws SQLException {
        ObservableList<String> items = getPoolNames();
        poolList.setItems(items);
    }

    private void changeTables(String poolItem) throws SQLException {
        clearTables();
        lessons = getLessons(poolItem);
        clientPaths = getReservations(poolItem);
        initializeLessons();
        initializePaths();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializePoolList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeLessons();
        initializePaths();
    }

    @FXML
    public void poolItemClicked() throws SQLException {

        String poolItem = poolList.getSelectionModel().getSelectedItem();

        if(poolItem == null) return;

        poolName = poolItem.split(",");
        System.out.println("clicked on " + poolName[0]);   //debug

        changeTables(poolName[0]);
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
}
}