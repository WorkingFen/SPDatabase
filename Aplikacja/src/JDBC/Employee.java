package JDBC;

import sample.AuditorEmployee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {

    private void addEmployee(Connection conn, String name, String surname, int perk, int postID, int poolID) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Pracownicy VALUES(?, ?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Pracownicy.Numer_Identyfikacyjny) FROM Pracownicy");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setInt(4, perk);
            stmt.setInt(5, postID);
            stmt.setInt(6, poolID);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, NULL, NULL, ?)");

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

    static int checkEmployee(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT PN FROM (SELECT Pracownicy_Numer_ID AS PN, Osoby.Numer_Identyfikacyjny FROM Osoby) WHERE Numer_Identyfikacyjny = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            int employeeID = rSet.getInt(1);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT SN FROM (SELECT Stanowiska_Numer_Stanowiska AS SN, Pracownicy.Numer_Identyfikacyjny AS ID FROM Pracownicy) WHERE ID = ?");
            stmt2.setInt(1, employeeID);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                return rSet2.getInt(1);
            }
            else{
                stmt2.close();
                rSet2.close();
                stmt.close();
                rSet.close();
                return 0;
            }
        }
        else{
            stmt.close();
            rSet.close();
            return 0;
        }
    }

    static public AuditorEmployee getAuditorEmployee(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT p.Imie, p.Nazwisko, p.Dodatek_do_Pensji, p.Stanowiska_Numer_Stanowiska FROM Pracownicy p WHERE Numer_Identyfikacyjny = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            int perk = rSet.getInt(3);
            int noPost = rSet.getInt(4);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Nazwa FROM Stanowiska WHERE Numer_Stanowiska = ?");
            stmt2.setInt(1, noPost);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                String postName = rSet2.getString(1);
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return new AuditorEmployee(id, name, surname, postName, perk);
            }
            else{
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return null;
            }
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
