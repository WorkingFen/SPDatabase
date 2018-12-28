package sample;

import javafx.scene.control.Button;

public class Lesson {
    private String name;
    private String date;

    private String enrolled;
    private String rescuer;
    private Button enrollButton;



    public Lesson(String name, String date, String enrolled, String rescuer) {
        this.name = name;
        this.date = date;
        this.enrolled = enrolled;
        this.rescuer = rescuer;
        this.enrollButton = new Button("Zapisz się");

        // change button color on mouse clicked
        this.enrollButton.setOnAction(e->{
            this.enrollButton.setStyle("-fx-background-color: #ff0000; ");
        });
    }
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public String getRescuer() {
        return rescuer;
    }

    public Button getEnrollButton() {
        return enrollButton;
    }

    public void setEnrollButton(Button enrollButton) {
        this.enrollButton = enrollButton;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public void setRescuer(String rescuer) {
        this.rescuer = rescuer;
    }
}
