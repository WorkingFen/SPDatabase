package sample;

import javafx.scene.control.Button;

public class ClientPath {
    private int number;
    private String date;
    private int pathNumber;
    private Button reserveButton;

    public ClientPath(int number, String date, int pathNumber, String msg){
        this.number = number;
        this.date = date;
        this.pathNumber = pathNumber;
        this.reserveButton = new Button(msg);

        // change button color on mouse clicked
        this.reserveButton.setOnAction(e->{
            this.reserveButton.setStyle("-fx-background-color: #ff0000; ");
        });
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPathNumber() { return pathNumber; }

    public void setPathNumber(int pathNumber) { this.pathNumber = pathNumber; }

    public Button getReserveButton() {
        return reserveButton;
    }

    public void setReserveButton(Button reserveButton) {
        this.reserveButton = reserveButton;
    }

}
