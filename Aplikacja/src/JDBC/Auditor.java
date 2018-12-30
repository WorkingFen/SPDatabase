package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auditor {

    static void addAuditor(Connection conn) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Audytorzy VALUES(?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Audytorzy.Numer_Identyfikacyjny) FROM Audytorzy");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, ?, NULL, NULL)");

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

    static boolean checkAuditor(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT AN FROM (SELECT Audytorzy_Numer_ID AS AN, Osoby.Numer_Identyfikacyjny FROM Osoby) WHERE Numer_Identyfikacyjny = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next())
            return rSet.getInt(1) != 0;
        else
            return false;
    }
}
