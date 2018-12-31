package sample;

import JDBC.Inspection;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RepairmanController implements Initializable {

    @FXML
    private TableView<RepairmanInspection> inspectionTable;
    @FXML
    private TableColumn<RepairmanInspection, String> inspectionID;
    @FXML
    private TableColumn<RepairmanInspection, String> inspectionDate;
    @FXML
    private TableColumn<RepairmanInspection, String> comment;
    @FXML
    private TableColumn<RepairmanInspection, String> delete;

    public RepairmanController() throws SQLException {
    }


    private ObservableList<RepairmanInspection> getInspections(Connection conn) throws SQLException {
        int noInspections;
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Przeglady");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) noInspections = rSet.getInt(1);
        else noInspections = 0;

        rSet.close();
        stmt.close();

        ObservableList<RepairmanInspection> list = FXCollections.observableArrayList();
        for(int i = 0; i < noInspections; i++){
            list.add(Inspection.getRepairmanInspection(conn, i+1));
        }
        return list;
    }

    private final ObservableList<RepairmanInspection> inspections = getInspections(Main.jdbc.getConn());

    private void initializeInspections() {
        inspectionID.setCellValueFactory(new PropertyValueFactory<>("id"));
        inspectionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        delete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        inspectionTable.getItems().addAll(inspections);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeInspections();
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
