package sample;

import javafx.scene.control.Button;

public class CashierClient {
    private String number;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Button editButton;
    private Button deleteButton;


    public CashierClient(String number, String firstName, String lastName, String phone, String email, Button editButton, Button deleteButton) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.editButton = editButton;
        this.deleteButton = deleteButton;


        // change button color on mouse clicked
        this.editButton.setOnAction(e -> {
            this.editButton.setStyle("-fx-background-color: #ff0000; ");
        });
        this.deleteButton.setOnAction(e -> {
            this.deleteButton.setStyle("-fx-background-color: #ff0000; ");
        });
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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