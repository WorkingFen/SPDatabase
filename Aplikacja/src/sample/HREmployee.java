package sample;

import javafx.scene.control.Button;

public class HREmployee {
    private int id;
    private String name;
    private String surname;
    private String position;
    private Button fireEmployee;
    private Button changePosition;

    public HREmployee(int id, String name, String surname, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.fireEmployee = new Button("Zwolnij");
        this.changePosition = new Button("Zmień stanowisko");
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

    public Button getChangePosition() { return changePosition; }

    public void setChangePosition(Button changePosition) { this.changePosition = changePosition; }
}
