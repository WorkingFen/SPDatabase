package sample.HR;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import sample.PopupWindows.PopupWindowAlert;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;


public class HREmployee {
    private HRController hc;
    private int id;
    private String name;
    private String surname;
    private String position;
    private Button fireEmployee;
    private ComboBox<String> changePosition;

    public HREmployee(HRController hc, int id, String name, String surname, String position) throws SQLException {
        this.hc = hc;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;

        if(!position.equals("deleted")){
            this.fireEmployee = new Button("Zwolnij");
            this.changePosition = new ComboBox<>();

            String[] list = hc.getPostsNames();

            // add items to the ComboBox
            this.changePosition.getItems().addAll(list);

            // set default value
            this.changePosition.getSelectionModel().select(position);

            // get ComboBox value after an action
            this.changePosition.setOnAction(e -> {
                {
                    String option = this.changePosition.getValue();
                    if(!position.equals(option)) {
                        boolean answer = PopupWindowAlert.display("Czy na pewno chcesz zmienić stanowisko?", "Zmień stanowisko", 350);
                        if (answer) {
                            try {
                                hc.changePost(option, id);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            this.changePosition.getSelectionModel().select(position);
                        }
                    }
                }
            });

            this.fireEmployee.setOnAction(e -> {
                boolean answer = PopupWindowAlert.display("Czy na pewno chcesz zwolnić pracownika?","Zwolnij pracownika", 350);
                if(answer){
                    try {
                        hc.fireEmployeeInstance(id);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    public HRController getHc() { return hc; }

    public void setHc(HRController hc) { this.hc = hc; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public Button getFireEmployee() { return fireEmployee; }

    public void setFireEmployee(Button fireEmployee) { this.fireEmployee = fireEmployee; }

    public ComboBox<String> getChangePosition() { return changePosition; }

    public void setChangePosition(ComboBox<String> changePosition) { this.changePosition = changePosition; }
}
