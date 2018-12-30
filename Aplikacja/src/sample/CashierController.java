package sample;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CashierController implements Initializable {

    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;


    @FXML
    private TableView<CashierClient> clientTable;
    @FXML
    private TableColumn<ClientPath, String> clientNumber;
    @FXML
    private TableColumn<ClientPath, String> clientFName;
    @FXML
    private TableColumn<ClientPath, String> clientLName;
    @FXML
    private TableColumn<ClientPath, String> clientPhone;
    @FXML
    private TableColumn<ClientPath, String> clientEMail;
    @FXML
    private TableColumn<ClientPath, String> editClient;
    @FXML
    private TableColumn<ClientPath, String> deleteClient;


    @FXML
    private TableView<CashierPath> pathTable;
    @FXML
    private TableColumn<ClientPath, String> lessonName;
    @FXML
    private TableColumn<ClientPath, String> lessonDate;
    @FXML
    private TableColumn<ClientPath, String> lessonEnrolled;
    @FXML
    private TableColumn<ClientPath, String> lessonRescuer;
    @FXML
    private TableColumn<ClientPath, String> reserveLesson;


    @FXML
    private TableView<CashierLesson> lessonTable;
    @FXML
    private TableColumn<ClientPath, String> pathReservationNumber;
    @FXML
    private TableColumn<ClientPath, String> pathHours;
    @FXML
    private TableColumn<ClientPath, String> pathNumber;
    @FXML
    private TableColumn<ClientPath, String> pathState;
    @FXML
    private TableColumn<ClientPath, String> reservePath;

    public CashierController() throws SQLException {
    }

    private ObservableList<CashierLesson> getLessons(Connection conn) throws SQLException {
        int noLessons;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Lekcje_Plywania");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noLessons = rSet.getInt(1);
        else noLessons = 0;

        rSet.close();
        stmt.close();

        ObservableList<CashierLesson> list = FXCollections.observableArrayList();
        for(int i = 0; i < noLessons; i++){
            list.add(Lesson.getCashierLesson(conn, "Zapisz", i+1));
        }
        return list;
    }

    private ObservableList<CashierPath> getReservations(Connection conn) throws SQLException {
        int noReservations;
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
    }

    private final ObservableList<CashierLesson> lessons = getLessons(Main.jdbc.getConn());

    private final ObservableList<CashierPath> paths = getReservations(Main.jdbc.getConn());

    private final ObservableList<CashierClient> clients =
            FXCollections.observableArrayList(
                    new CashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń")),
                    new CashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń")),
                    new CashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń")),
                    new CashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń"))
            );

    private void initializeLessons() {

        // ustawienie typu kolumn czy coś
        lessonName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonEnrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        lessonRescuer.setCellValueFactory(new PropertyValueFactory<>("rescuer"));
        reserveLesson.setCellValueFactory(new PropertyValueFactory<>("enrollButton"));

        // dodanie wierszy
        lessonTable.getItems().addAll(lessons);
    }

    private void initializePaths() {

        // ustawienie typu kolumn czy coś
        pathReservationNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        pathHours.setCellValueFactory(new PropertyValueFactory<>("date"));
        pathNumber.setCellValueFactory(new PropertyValueFactory<>("pathNumber"));
        pathState.setCellValueFactory(new PropertyValueFactory<>("state"));
        reservePath.setCellValueFactory(new PropertyValueFactory<>("reserveButton"));

        // dodanie wierszy
        pathTable.getItems().addAll(paths);
    }

    private void initializeClients() {

        // ustawienie typu kolumn czy coś
        clientNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        clientFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        clientLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clientPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clientEMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        editClient.setCellValueFactory(new PropertyValueFactory<>("editButton"));
        deleteClient.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        // dodanie wierszy
        clientTable.getItems().addAll(clients);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeLessons();
        initializePaths();
        initializeClients();

    }

    public void addClientButtonPushed(ActionEvent event) throws IOException {

        String fnameInput= fnameField.getText();
        String lnameInput = lnameField.getText();
        String phoneNumberInput = phoneNumberField.getText();
        String emailInput = emailField.getText();


        CashierClient client = new CashierClient("1337",fnameInput, lnameInput, phoneNumberInput, emailInput,new Button("Edytuj"),new Button("Usuń"));

        clientTable.getItems().add(client);

        //TODO dodanie do BD i jakieś ify
    }

    public void logOutButtonPushed(ActionEvent event)throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}

