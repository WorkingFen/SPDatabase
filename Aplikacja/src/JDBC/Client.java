package JDBC;

import sample.CashierClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

    private void addClient(Connection conn, String name, String surname, String telNumber, String email) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Klienci VALUES(?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Klienci.Numer_klienta) FROM Klienci");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, telNumber);
            stmt.setString(5, email);
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

    static public CashierClient getCashierClient(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT k.Imie, k.Nazwisko, k.Numer_Telefonu, k.\"Adres_e-mail\" FROM Klienci k WHERE Numer_Klienta = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            String phone = rSet.getString(3);
            String email = rSet.getString(4);
            rSet.close();
            stmt.close();
            return new CashierClient(id, name, surname, phone, email);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
