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
        PreparedStatement stmt = conn.prepareStatement("SELECT rt.Data_i_Godzina, rt.Numer_toru, rt.Status FROM Rezerwacje_Toru rt WHERE Numer_Rezerwacji = ? AND Data_I_Godzina > SYSDATE");
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

    static public ClientPath getClientReservation(Connection conn, String msg, int id, String poolItem) throws SQLException {
        String date;
        int noPath;
        PreparedStatement stmt = conn.prepareStatement("SELECT Data_i_Godzina, Numer_Toru FROM" +
                "((SELECT Data_i_Godzina, Numer_Toru, Baseny_Numer_Obiektu FROM " +
                "(SELECT rt.Data_i_Godzina, rt.Numer_Toru, u.Baseny_Numer_Obiektu, rt.Status, rt.Numer_Rezerwacji FROM Rezerwacje_Toru rt JOIN Uslugi u ON rt.Ogolne_Numer_uslugi = u.Ogolne_Numer_Uslugi) " +
                "WHERE Numer_Rezerwacji = ? AND Status = 0 AND Data_I_Godzina > SYSDATE) a JOIN Baseny b ON a.Baseny_Numer_Obiektu = b.Numer_Obiektu)" +
                "WHERE Nazwa_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setString(2, poolItem);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            date = rSet.getString(1);
            noPath = rSet.getInt(2);
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
