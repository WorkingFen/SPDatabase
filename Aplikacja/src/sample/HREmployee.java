package sample;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class HREmployee {
    private int id;
    private String name;
    private String surname;
    private String position;
    private Button fireEmployee;
    private ComboBox<String> changePosition;

    public HREmployee(int id, String name, String surname, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.fireEmployee = new Button("Zwolnij");
        this.changePosition = new ComboBox<>();
		
		// add items to the ComboBox
        this.changePosition.getItems().addAll("Piekarz",
                ": D",
                "XD");

        // get ComboBox value after an action
        this.changePosition.setOnAction(e -> {
            {
                Object option = this.changePosition.getValue();
                System.out.println(option);
            }
        });
		
		// alert
        this.fireEmployee.setOnAction(e -> {
            boolean answer = PopupWindowAlert.display("Czy na pewno chcesz zwolnić śmierdziela?","Rozwolnienie", 350);
            System.out.println(answer);
        });		
    }

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
