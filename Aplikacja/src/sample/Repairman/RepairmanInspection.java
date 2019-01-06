package sample.Repairman;

import javafx.scene.control.Button;
import sample.PopupWindows.PopupWindowAlert;

import java.sql.SQLException;

public class RepairmanInspection {
    private RepairmanController rc;
    private int id;
    private String date;
    private String comment;
    private Button delete;

    public RepairmanInspection(RepairmanController rc, int id, String date, String comment) {
        this.rc = rc;
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.delete = new Button("Usuń");

        this.delete.setOnAction(e -> {
            boolean answer = PopupWindowAlert.display("Czy na pewno chcesz usunąć ten przegląd?","Usuwanie przeglądu", 350);
            if(answer){
                try {
                    rc.deleteInspection(id);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                System.out.println("No to elo");
            }
        });
    }

    public RepairmanController getRc() { return rc; }

    public void setRc(RepairmanController rc) { this.rc = rc; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Button getDelete() { return delete; }

    public void setDelete(Button delete) { this.delete = delete; }


}
