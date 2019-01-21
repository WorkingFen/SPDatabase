package sample.Cashier;

import javafx.scene.control.Button;

public class CashierPath {
    private CashierController cc;
    private int number;
    private String date;
    private int pathNumber;
    private String state;
    private Button statusButton;

    public CashierPath(CashierController cc, int number, String date, int pathNumber, String state){
        this.cc = cc;
        this.number = number;
        this.date = date;
        this.pathNumber = pathNumber;
        this.state = state;
        this.statusButton = new Button("Skorzystano");
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

    public CashierController getCc() { return cc; }

    public void setCc(CashierController cc) { this.cc = cc; }

    public Button getStatusButton() { return statusButton; }

    public void setStatusButton(Button statusButton) { this.statusButton = statusButton; }
}
