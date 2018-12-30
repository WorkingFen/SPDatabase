package JDBC;

import sample.CashierPath;
import sample.ClientPath;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {

    private void addReservation(Connection conn, String date, int noPath, String status, int clientID, int generalID) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Rezerwacje_Toru VALUES(?, to_date(?, 'YY/MM/DD HH24:MM'), ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Rezerwacje_Toru.Numer_rezerwacji) FROM Rezerwacje_Toru");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, date);
            stmt.setInt(3, noPath);
            stmt.setString(4, status);
            stmt.setInt(5, clientID);
            stmt.setInt(6, generalID);
            stmt.executeQuery();
            stmt.close();
            conn.commit();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            conn.rollback();
        }
        finally {
            conn.setAutoCommit(true);
        }
    }

    static public CashierPath getCashierReservation(Connection conn, String msg, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT rt.Data_i_Godzina, rt.Numer_toru, rt.Status FROM Rezerwacje_Toru rt WHERE Numer_Rezerwacji = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            int noPath = rSet.getInt(2);
            int state = rSet.getInt(3);
            String status;
            if(state == 0) status = "Wolny";
            else status = "Zajęty";
            rSet.close();
            stmt.close();
            return new CashierPath(id, date, noPath, status, msg);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public ClientPath getClientReservation(Connection conn, String msg, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT rt.Data_i_Godzina, rt.Numer_toru FROM Rezerwacje_Toru rt WHERE Numer_Rezerwacji = ? AND Status = 0");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            int noPath = rSet.getInt(2);
            rSet.close();
            stmt.close();
            return new ClientPath(id, date, noPath, msg);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
