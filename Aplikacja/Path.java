package sample;

import javafx.scene.control.Button;

public class Path {
    private String number;
    private String date;
    private Button reserveButton;

    public Path(String number, String date){
        this.number = number;
        this.date = date;
        this.reserveButton = new Button("Zarezerwuj");

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

    public Button getReserveButton() {
        return reserveButton;
    }

    public void setReserveButton(Button reserveButton) {
        this.reserveButton = reserveButton;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}
