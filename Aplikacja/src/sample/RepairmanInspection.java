package sample;

import javafx.scene.control.Button;

public class RepairmanInspection {
    private int id;
    private String date;
    private String comment;
    private Button delete;

    public RepairmanInspection(int id, String date, String comment) {
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.delete = new Button("Usuń");
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Button getDelete() { return delete; }

    public void setDelete(Button delete) { this.delete = delete; }
}
