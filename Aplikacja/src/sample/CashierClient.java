package sample;

import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class CashierClient {
    private CashierController cc;
    private int number;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Button editButton;
    private Button deleteButton;

    public CashierClient(CashierController cc, int number, String firstName, String lastName, String phone, String email) {
        this.cc = cc;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.editButton = new Button("Edytuj");
        this.deleteButton = new Button("Usuń");

        this.editButton.setOnAction(e -> {
            {
                String[] fieldFilling = new String[] {firstName, lastName, phone, email};
                String[] textFieldArray = new String[] {"Imię", "Nazwisko", "Numer telefonu", "Adres e-mail"};
                try {
                    String []array = PopupWindowEdit.display(fieldFilling, textFieldArray,"Edycja","Wprowadź dane:", 350);
                    if(array != null) cc.editClientInstance(number, array[0], array[1], array[2], array[3]);
                } catch (IOException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.deleteButton.setOnAction(e -> {
            boolean answer = PopupWindowAlert.display("Czy na pewno chcesz usunąć tego klienta?","Usuwanie klienta", 350);
            if(answer){
                try {
                    cc.deleteClientInstance(firstName, lastName, email);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public CashierController getCc() { return cc; }

    public void setCc(CashierController cc) { this.cc = cc; }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
