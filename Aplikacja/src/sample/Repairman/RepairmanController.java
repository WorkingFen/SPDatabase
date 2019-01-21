package sample.Repairman;

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

public class RepairmanController implements Initializable {

    @FXML
    private TextField dateField;
    @FXML
    private TextField poolField;


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

    private ObservableList<RepairmanInspection> getInspections() throws SQLException {
        Connection conn = Main.jdbc.getConn();
        int minInspection;
        int maxInspection;

        PreparedStatement stmt = conn.prepareStatement("SELECT MIN(Numer_Przegladu) FROM Przeglady");
        ResultSet rSet = stmt.executeQuery();

        if(rSet.next()) minInspection = rSet.getInt(1);
        else minInspection = 0;

        rSet.close();
        stmt.close();

        stmt = conn.prepareStatement("SELECT MAX(Numer_Przegladu) FROM Przeglady");
        rSet = stmt.executeQuery();

        if(rSet.next()) maxInspection = rSet.getInt(1);
        else maxInspection = 0;

        rSet.close();
        stmt.close();

        ObservableList<RepairmanInspection> list = FXCollections.observableArrayList();
        for(int i = minInspection-1; i < maxInspection; i++){
            RepairmanInspection temp = Inspection.getRepairmanInspection(this, conn, i+1);
            if(temp != null) list.add(temp);
        }
        return list;
    }

    void deleteInspection(int id) throws SQLException {
        Inspection.deleteInspection(Main.jdbc.getConn(), id);
        inspectionTable.getItems().clear();
        inspections = getInspections();
        initializeInspections();
    }

    private void addInspection(String date, String poolName) throws SQLException {
        Inspection.addInspection(Main.jdbc.getConn(), date, poolName);
        inspectionTable.getItems().clear();
        inspections = getInspections();
        initializeInspections();

        dateField.setText(null);
        poolField.setText(null);
    }

    private ObservableList<RepairmanInspection> inspections = getInspections();

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

    public void addClientButtonPushed(ActionEvent event) throws IOException {

        String dateInput = dateField.getText();
        String poolInput = poolField.getText();

        if(dateInput.equals("") || poolInput.equals("")) return;

        boolean answer = PopupWindowAlert.display("Czy na pewno chcesz dodać ten przegląd?","Dodawanie przeglądu", 350);
        if(answer){
            try {
                this.addInspection(dateInput, poolInput);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Logowanie");
        window.setScene(tableViewScene);
        window.show();
    }
}
