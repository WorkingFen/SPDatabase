package sample;

import javafx.scene.control.Button;

public class cashierPath {
    private String number;
    private String date;
    private String pathNumber;
    private String state;
    private Button reserveButton;



    public cashierPath(String name, String date, String pathNumber, String state) {
        this.number = name;
        this.date = date;
        this.pathNumber = pathNumber;
        this.state = state;
        this.reserveButton = new Button("Rezerwuj");

        // change button color on mouse clicked
        this.reserveButton.setOnAction(e->{
            this.reserveButton.setStyle("-fx-background-color: #ff0000; ");
        });
}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPathNumber() {
        return pathNumber;
    }

    public void setPathNumber(String pathNumber) {
        this.pathNumber = pathNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Button getReserveButton() {
        return reserveButton;
    }

    public void setReserveButton(Button enrollButton) {
        this.reserveButton = enrollButton;
    }
}
