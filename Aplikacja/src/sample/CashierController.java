package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierController implements Initializable {


    @FXML
    private TableView<cashierClient> clientTable;
    @FXML
    private TableColumn<Path, String> clientNumber;
    @FXML
    private TableColumn<Path, String> clientFName;
    @FXML
    private TableColumn<Path, String> clientLName;
    @FXML
    private TableColumn<Path, String> clientPhone;
    @FXML
    private TableColumn<Path, String> clientEMail;
    @FXML
    private TableColumn<Path, String> editClient;
    @FXML
    private TableColumn<Path, String> deleteClient;


    @FXML
    private TableView<cashierPath> pathTable;
    @FXML
    private TableColumn<Path, String> lessonName;
    @FXML
    private TableColumn<Path, String> lessonDate;
    @FXML
    private TableColumn<Path, String> lessonEnrolled;
    @FXML
    private TableColumn<Path, String> lessonRescuer;
    @FXML
    private TableColumn<Path, String> reserveLesson;


    @FXML
    private TableView<cashierLesson> lessonTable;
    @FXML
    private TableColumn<Path, String> pathReservationNumber;
    @FXML
    private TableColumn<Path, String> pathHours;
    @FXML
    private TableColumn<Path, String> pathNumber;
    @FXML
    private TableColumn<Path, String> pathState;
    @FXML
    private TableColumn<Path, String> reservePath;


    // przykładowe wiersze do dodania do tabel
    private final ObservableList<cashierLesson> lessons =
            FXCollections.observableArrayList(
                    new cashierLesson("Banan", "02.02.2020", "25:61", "Srarol"),
                    new cashierLesson("Banan", "02.02.2020", "25:61", "Srarol"),
                    new cashierLesson("Banan", "02.02.2020", "25:61", "Srarol"),
                    new cashierLesson("Banan", "02.02.2020", "25:61", "Srarol")
            );

    private final ObservableList<cashierPath> paths =
            FXCollections.observableArrayList(
                    new cashierPath("Banan", "02.02.2020", "25:61", "Srarol"),
                    new cashierPath("Banan", "02.02.2020", "25:61", "Srarol"),
                    new cashierPath("Banan", "02.02.2020", "25:61", "Srarol"),
                    new cashierPath("Banan", "02.02.2020", "25:61", "Srarol")
            );
    private final ObservableList<cashierClient> clients =
            FXCollections.observableArrayList(
                    new cashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń")),
                    new cashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń")),
                    new cashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń")),
                    new cashierClient("Banan", "02.02.2020", "25:61", "Srarol", "A", new Button("Edytuj"), new Button("Usuń"))
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
}

