package sample;

import javafx.scene.control.Button;

public class CashierClient {
    private int number;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Button editButton;
    private Button deleteButton;

    public CashierClient(int number, String firstName, String lastName, String phone, String email) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.editButton = new Button("Edytuj");
        this.deleteButton = new Button("Usuń");

         this.editButton.setOnAction(e -> {
            {
                String [] textFieldArray = new String[] {"Imię", "Nazwisko", "Numer telefonu", "Adres e-mail"};
                try {
                    String []array = PopupWindowEdit.display(textFieldArray,"Edycja","Wprowadź dane:", 350);
                    System.out.println(Arrays.toString(array));

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.deleteButton.setOnAction(e -> {
            this.deleteButton.setStyle("-fx-background-color: #ff0000; ");
        });
    }

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
