package sample.Client;

import javafx.scene.control.Button;

public class ClientLesson {
    private String date;
    private String enrolled;
    private String rescuer;
    private Button enrollButton;

    public ClientLesson(String date, String enrolled, String rescuer, String msg) {
        this.date = date;
        this.enrolled = enrolled;
        this.rescuer = rescuer;
        this.enrollButton = new Button(msg);

        this.enrollButton.setOnAction(e->{
            this.enrollButton.setStyle("-fx-background-color: #ff0000; ");
        });
    }

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
