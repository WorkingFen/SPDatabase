package JDBC;

import sample.Auditor.AuditorEmployee;
import sample.HR.HRController;
import sample.HR.HREmployee;
import sample.Manager.ManagerEmployee;
import sample.Owner.OwnerEmployee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {

    static public void addEmployee(Connection conn, String name, String surname, String post, int poolNo) throws SQLException {
        int postID;
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("SELECT Numer_Stanowiska FROM Stanowiska WHERE Nazwa = ?");
            stmt.setString(1, post);
            ResultSet rSet = stmt.executeQuery();

            if(rSet.next()){
                postID = rSet.getInt(1);
                rSet.close();
                stmt.close();
            }
            else{
                rSet.close();
                stmt.close();
                return;
            }

            stmt=conn.prepareStatement("INSERT INTO Pracownicy VALUES(?, ?, ?, 0, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Pracownicy.Numer_Identyfikacyjny) FROM Pracownicy");
            rSet = stmt2.executeQuery();

            int id;
            if(rSet.next()) id = rSet.getInt(1)+1;
            else id = 1;

            rSet.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setInt(4, postID);
            stmt.setInt(5, poolNo);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, NULL, NULL, ?)");

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rSet = stmt2.executeQuery();

            int personID;
            if(rSet.next()) personID = rSet.getInt(1)+1;
            else personID = 1;

            rSet.close();
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

    public static void deleteEmployee(Connection conn, int id) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("UPDATE Pracownicy SET Stanowiska_Numer_Stanowiska = 0, Baseny_Numer_Obiektu = 0, Dodatek_Do_Pensji = 0 WHERE Numer_Identyfikacyjny = ?");

            stmt.setInt(1, id);
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

    static public void changeEmployeePost(Connection conn, String postName, int id) throws SQLException {
        int noPost;
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("SELECT Numer_Stanowiska FROM Stanowiska WHERE Nazwa = ?");
            stmt.setString(1, postName);
            ResultSet rSet = stmt.executeQuery();

            if(rSet.next()){
                noPost = rSet.getInt(1);
                rSet.close();
                stmt.close();
            }
            else{
                rSet.close();
                stmt.close();
                return;
            }

            stmt=conn.prepareStatement("UPDATE Pracownicy SET Stanowiska_Numer_Stanowiska = ? WHERE Numer_Identyfikacyjny = ?");

            stmt.setInt(1, noPost);
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

    static public AuditorEmployee getAuditorEmployee(Connection conn, int id, String poolName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT Imie, Nazwisko, Dodatek_do_Pensji, Nazwa FROM " +
                        "(SELECT a.Numer_Identyfikacyjny, a.Imie, a.Nazwisko, a.Dodatek_Do_Pensji, a.Nazwa, b.Nazwa_Obiektu FROM " +
                            "(SELECT p.Numer_Identyfikacyjny, p.Imie, p.Nazwisko, p.Dodatek_Do_Pensji, p.Baseny_Numer_Obiektu, s.Nazwa FROM Pracownicy p JOIN Stanowiska s ON p.Stanowiska_Numer_Stanowiska = s.Numer_Stanowiska) a " +
                        "JOIN Baseny b ON a.Baseny_Numer_Obiektu = b.Numer_Obiektu) " +
                    "WHERE Numer_Identyfikacyjny = ? AND Nazwa_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setString(2, poolName);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            int perk = rSet.getInt(3);
            String postName = rSet.getString(4);
            rSet.close();
            stmt.close();
            if(postName.equals("deleted")) return null;
            return new AuditorEmployee(id, name, surname, postName, perk);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public HREmployee getHREmployee(HRController hc, Connection conn, int id, int poolNo) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT p.Imie, p.Nazwisko, p.Stanowiska_Numer_Stanowiska FROM Pracownicy p WHERE Numer_Identyfikacyjny = ? AND Baseny_Numer_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setInt(2, poolNo);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            int noPost = rSet.getInt(3);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Nazwa FROM Stanowiska WHERE Numer_Stanowiska = ?");
            stmt2.setInt(1, noPost);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                String postName = rSet2.getString(1);
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                if(postName.equals("deleted")) return null;
                return new HREmployee(hc, id, name, surname, postName);
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

    static public OwnerEmployee getOwnerEmployee(Connection conn, int id, String poolName) throws SQLException {
        String postName;
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT Imie, Nazwisko, Dodatek_Do_Pensji, Nazwa FROM " +
                        "(SELECT a.Imie, a.Nazwisko, a.Dodatek_Do_Pensji, a.Numer_Identyfikacyjny, a.Nazwa_Obiektu, s.Nazwa FROM " +
                            "(SELECT p.Imie, p.Nazwisko, p.Dodatek_Do_Pensji, p.Numer_Identyfikacyjny, p.Stanowiska_Numer_Stanowiska, b.Nazwa_Obiektu FROM Pracownicy p JOIN Baseny b ON b.Numer_Obiektu = p.Baseny_Numer_Obiektu) a " +
                        "JOIN Stanowiska s ON s.Numer_Stanowiska = a.Stanowiska_Numer_Stanowiska) " +
                    "WHERE Numer_Identyfikacyjny = ? AND Nazwa_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setString(2, poolName);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            int perk = rSet.getInt(3);
            postName = rSet.getString(4);
            rSet.close();
            stmt.close();
            if(postName.equals("deleted")) return null;
            return new OwnerEmployee(id, name, surname, perk);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public ManagerEmployee getManagerEmployee(Connection conn, int id, int poolNo) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT p.Imie, p.Nazwisko, p.Stanowiska_Numer_Stanowiska FROM Pracownicy p WHERE Numer_Identyfikacyjny = ? AND Baseny_Numer_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setInt(2, poolNo);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            int noPost = rSet.getInt(3);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Nazwa FROM Stanowiska WHERE Numer_Stanowiska = ?");
            stmt2.setInt(1, noPost);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                String postName = rSet2.getString(1);
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                if(postName.equals("deleted")) return null;
                return new ManagerEmployee(id, name, surname, postName);
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
