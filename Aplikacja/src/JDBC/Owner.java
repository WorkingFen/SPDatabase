package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Owner {

    private void addOwner(Connection conn, String name, String surname) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Wlasciciele VALUES(?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Wlasciciele.Numer_Identyfikacyjny) FROM Wlasciciele");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, NULL, ?, NULL)");

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rset = stmt2.executeQuery();

            int personID;
            if(rset.next()) personID = rset.getInt(1)+1;
            else personID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, personID);
            stmt.setInt(2, id);
            stmt.executeQuery();
            stmt.close();
            conn.commit();
        }

        catch(Exception e){
            System.out.println(e.getMessage());
            conn.rollback();
        }
        finally{
            conn.setAutoCommit(true);
        }
    }

    static boolean checkOwner(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT WN FROM (SELECT Wlasciciele_Numer_ID AS WN, Osoby.Numer_Identyfikacyjny FROM Osoby) WHERE Numer_Identyfikacyjny = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next())
            return rSet.getInt(1) != 0;
        else
            return false;
    }
}
