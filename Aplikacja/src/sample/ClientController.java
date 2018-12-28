package sample;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {


    // zmienne potrzebne do dodawania lekcji
    @FXML
    private TableView<Lesson> lessonTable;
    @FXML
    private TableColumn<Path, String> pathNumber;
    @FXML
    private TableColumn<Path, String> pathHours;
    @FXML
    private TableColumn<Path, Button> reservePath;

    // zmienne potrzebne do dodawania torów
    @FXML
    private TableView<Path> pathTable;
    @FXML
    private TableColumn<Path, String> lessonName;
    @FXML
    private TableColumn<Path, String> lessonDate;
    @FXML
    private TableColumn<Path, String> lessonEnrolled;
    @FXML
    private TableColumn<Path, String> lessonRescuer;
    @FXML
    private TableColumn<Path, Button> reserveLesson;


    // przykładowe wiersze do dodania do tabel
    private final ObservableList<Lesson> lessons =
            FXCollections.observableArrayList(
                    new Lesson("Banan", "02.02.2020", "25:61", "Srarol"),
                    new Lesson("Banan", "02.02.2020", "25:61", "Srarol"),
                    new Lesson("Banan", "02.02.2020", "25:61", "Srarol"),
                    new Lesson("Banan", "02.02.2020", "25:61", "Srarol")
            );
    private final ObservableList<Path> paths =
            FXCollections.observableArrayList(
                    new Path("Banan", "02.02.2020"),
                    new Path("Banan", "02.02.2020"),
                    new Path("Banan", "02.02.2020"),
                    new Path("Banan", "02.02.2020")
            );


    private void initializeLessons(){

        // ustawienie typu kolumn czy coś
        lessonName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonEnrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        lessonRescuer.setCellValueFactory(new PropertyValueFactory<>("rescuer"));
        reserveLesson.setCellValueFactory(new PropertyValueFactory<>("enrollButton"));

        // dodanie wierszy
        lessonTable.getItems().addAll(lessons);
    }

    private void initializePaths(){
        pathNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        pathHours.setCellValueFactory(new PropertyValueFactory<>("date"));
        reservePath.setCellValueFactory(new PropertyValueFactory<>("reserveButton"));

        pathTable.getItems().addAll(paths);
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