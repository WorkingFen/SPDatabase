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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    // zmienne potrzebne do dodawania torów
    @FXML
    private TableView<ClientPath> pathTable;
    @FXML
    private TableColumn<ClientPath, String> number;
    @FXML
    private TableColumn<ClientPath, String> pathHours;
    @FXML
    private TableColumn<ClientPath, String> pathNumber;
    @FXML
    private TableColumn<ClientPath, String> pathPool;
    @FXML
    private TableColumn<ClientPath, Button> reservePath;

    // zmienne potrzebne do dodawania lekcji
    @FXML
    private TableView<ClientLesson> lessonTable;
    @FXML
    private TableColumn<ClientLesson, String> lessonDate;
    @FXML
    private TableColumn<ClientLesson, String> lessonEnrolled;
    @FXML
    private TableColumn<ClientLesson, String> lessonRescuer;
    @FXML
    private TableColumn<ClientLesson, String> lessonPool;
    @FXML
    private TableColumn<ClientLesson, Button> reserveLesson;

    public ClientController() throws SQLException {
    }

    private ObservableList<ClientLesson> getLessons(Connection conn) throws SQLException {
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
            ClientLesson temp = Lesson.getClientLesson(conn, "Zapisz się", i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private ObservableList<ClientPath> getReservations(Connection conn) throws SQLException {
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
            ClientPath temp = Reservation.getClientReservation(conn, "Zarezerwuj", i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    private final ObservableList<ClientLesson> lessons = getLessons(Main.jdbc.getConn());

    private final ObservableList<ClientPath> clientPaths = getReservations(Main.jdbc.getConn());

    private void initializeLessons(){

        // ustawienie typu kolumn czy coś
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonEnrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        lessonRescuer.setCellValueFactory(new PropertyValueFactory<>("rescuer"));
        lessonPool.setCellValueFactory(new PropertyValueFactory<>("poolName"));
        reserveLesson.setCellValueFactory(new PropertyValueFactory<>("enrollButton"));

        // dodanie wierszy
        lessonTable.getItems().addAll(lessons);
    }

    private void initializePaths(){
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        pathHours.setCellValueFactory(new PropertyValueFactory<>("date"));
        pathNumber.setCellValueFactory(new PropertyValueFactory<>("pathNumber"));
        pathPool.setCellValueFactory(new PropertyValueFactory<>("poolName"));
        reservePath.setCellValueFactory(new PropertyValueFactory<>("reserveButton"));

        pathTable.getItems().addAll(clientPaths);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeLessons();
        initializePaths();

    }

    // powrót do okna logowania
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
}
}