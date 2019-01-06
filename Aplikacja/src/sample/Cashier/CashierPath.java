package sample.Cashier;

import javafx.scene.control.Button;

public class CashierPath {
    private int number;
    private String date;
    private int pathNumber;
    private String state;
    private Button reserveButton;
    private Button cancelButton;

    public CashierPath(int number, String date, int pathNumber, String state, String msgIn, String msgOut){
        this.number = number;
        this.date = date;
        this.pathNumber = pathNumber;
        this.state = state;
        if(state.equals("Wolny")) this.reserveButton = new Button(msgIn);
        else if(state.equals("Zajęty")) this.cancelButton = new Button(msgOut);

        if(reserveButton != null){
            this.reserveButton.setOnAction(e->{
                this.reserveButton.setStyle("-fx-background-color: #ff0000; ");
            });
        }
        if(cancelButton != null){
            this.cancelButton.setOnAction(e->{
                this.cancelButton.setStyle("-fx-background-color: #ff0000; ");
            });
        }
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Button getCancelButton() { return cancelButton; }

    public void setCancelButton(Button cancelButton) { this.cancelButton = cancelButton; }

    public Button getReserveButton() {
        return reserveButton;
    }

    public void setReserveButton(Button reserveButton) {
        this.reserveButton = reserveButton;
    }

}
