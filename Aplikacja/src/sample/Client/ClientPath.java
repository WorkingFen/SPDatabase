package sample.Client;

import javafx.scene.control.Button;
import sample.PopupWindows.PopupWindowChoose;
import sample.PopupWindows.PopupWindowEdit;

import java.io.IOException;
import java.sql.SQLException;

public class ClientPath {
    private ClientController cc;
    private int number;
    private String date;
    private int pathNumber;

    public ClientPath(ClientController cc, int number, String date, int pathNumber){
        this.cc = cc;
        this.number = number;
        this.date = date;
        this.pathNumber = pathNumber;
    }

    public ClientController getCc() { return cc; }

    public void setCc(ClientController cc) { this.cc = cc; }

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

}
