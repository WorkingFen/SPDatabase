package sample.Client;

import javafx.scene.control.Button;
import sample.PopupWindows.PopupWindowChoose;
import sample.PopupWindows.PopupWindowEdit;

import java.io.IOException;
import java.sql.SQLException;

public class ClientLesson {
    private ClientController cc;
    private int id;
    private String date;
    private String enrolled;
    private String rescuer;
    private Button enrollButton;

    public ClientLesson(ClientController cc, int id, String date, String enrolled, String rescuer, String msg) {
        this.cc = cc;
        this.id = id;
        this.date = date;
        this.enrolled = enrolled;
        this.rescuer = rescuer;
        this.enrollButton = new Button(msg);

        this.enrollButton.setOnAction(e->{
            int answer = PopupWindowChoose.display("Wybierz jaki klient dokonuje zapisu: ","Zapis na lekcję", 350);
            if(answer==1){
                String[] fieldFilling = new String[] {null, null, null};
                String[] textFieldArray = new String[] {"Imię", "Nazwisko", "Numer telefonu"};
                try {
                    String []array = PopupWindowEdit.display(fieldFilling, textFieldArray,"Wybierz klienta","Wprowadź dane:", 350);
                    if(array != null){
                        int clientID = cc.getClientInstance(array[0], array[1], array[2]);
                        if(clientID != 0) cc.addAttendee(id, clientID);
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
                        if(clientID != 0) cc.addAttendee(id, clientID);
                    }
                } catch (IOException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public ClientController getCc() { return cc; }

    public void setCc(ClientController cc) { this.cc = cc; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRescuer() {
        return rescuer;
    }

    public void setRescuer(String rescuer) {
        this.rescuer = rescuer;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public Button getEnrollButton() {
        return enrollButton;
    }

    public void setEnrollButton(Button enrollButton) {
        this.enrollButton = enrollButton;
    }
}
