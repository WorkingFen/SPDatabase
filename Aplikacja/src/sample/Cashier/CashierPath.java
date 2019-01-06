package sample.Cashier;

import javafx.scene.control.Button;
import sample.PopupWindows.PopupWindowChoose;
import sample.PopupWindows.PopupWindowEdit;

import java.io.IOException;
import java.sql.SQLException;

public class CashierPath {
    private CashierController cc;
    private int number;
    private String date;
    private int pathNumber;
    private String state;
    private Button reserveButton;
    private Button cancelButton;

    public CashierPath(CashierController cc, int number, String date, int pathNumber, String state, String msgIn, String msgOut){
        this.cc = cc;
        this.number = number;
        this.date = date;
        this.pathNumber = pathNumber;
        this.state = state;
        if(state.equals("Wolny")) this.reserveButton = new Button(msgIn);
        else if(state.equals("Zajęty")) this.cancelButton = new Button(msgOut);

        if(reserveButton != null){
            this.reserveButton.setOnAction(e->{
                int answer = PopupWindowChoose.display("Wybierz jaki klient dokonuje rezerwacji: ","Rezerwacja toru", 350);
                if(answer==1){
                    String[] fieldFilling = new String[] {null, null, null};
                    String[] textFieldArray = new String[] {"Imię", "Nazwisko", "Numer telefonu"};
                    try {
                        String []array = PopupWindowEdit.display(fieldFilling, textFieldArray,"Wybierz klienta","Wprowadź dane:", 350);
                        if(array != null){
                            int clientID = cc.getClientInstance(array[0], array[1], array[2]);
                            if(clientID != 0) cc.addReservation(number, clientID);
                        }
                    } catch (IOException | SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                else if(answer==2){
                    String[] fieldFilling = new String[] {null, null, null, null};
                    String[] textFieldArray = new String[] {"Imię", "Nazwisko", "Numer telefonu", "Adres e-mail"};
                    try {
                        String []array = PopupWindowEdit.display(fieldFilling, textFieldArray,"Nowy klient","Wprowadź dane:", 350);
                        if(array != null){
                            if(array[0] == null || array[1] == null || array[2] == null) return;
                            int clientID = cc.addNewClient(array[0], array[1], array[2], array[3]);
                            if(clientID != 0) cc.addReservation(number, clientID);
                        }
                    } catch (IOException | SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        if(cancelButton != null){
            this.cancelButton.setOnAction(e->{
                String[] fieldFilling = new String[] {null, null, null};
                String[] textFieldArray = new String[] {"Imię", "Nazwisko", "Numer telefonu"};
                try {
                    String []array = PopupWindowEdit.display(fieldFilling, textFieldArray,"Wybierz klienta","Wprowadź dane:", 350);
                    if(array != null){
                        int clientID = cc.getClientInstance(array[0], array[1], array[2]);
                        if(clientID != 0) cc.deleteReservation(number, clientID);
                    }
                } catch (IOException | SQLException e1) {
                    e1.printStackTrace();
                }
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
