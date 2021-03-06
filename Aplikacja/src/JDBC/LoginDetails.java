package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDetails {

    private void addLoginDetails(Connection conn, String login, String password) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Dane_Do_Logowania VALUES(?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Dane_Do_Logowania.Id) FROM Dane_Do_Logowania");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rset = stmt2.executeQuery();

            int personID;
            if(rset.next()) personID = rset.getInt(1)+1;
            else personID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setInt(4, personID);
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

    public static int checkLoginDetails(Connection conn, String login, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Osoby_Numer_Identyfikacyjny FROM Dane_Do_Logowania WHERE Login = ?");
        stmt.setString(1, login);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            int logNum = rSet.getInt(1);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT RN FROM (SELECT Haslo AS HA, ROWNUM AS RN FROM Generator_Passwords) WHERE HA = ?");
            stmt2.setString(1, password);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                int passNum = rSet2.getInt(1);
                if(passNum == logNum){
                    int source = Employee.checkEmployee(conn, passNum);
                    if(source != 0){
                        return source;
                    }
                    else if(Owner.checkOwner(conn, passNum)){
                        return 6;
                    }
                    else if(Auditor.checkAuditor(conn, passNum)) {
                        return 7;
                    }
                    else
                        return 0;
                }
            }
            rSet2.close();
            stmt2.close();
            rSet.close();
            stmt.close();
            return 0;
        }
        else{
            rSet.close();
            stmt.close();
            return 0;
        }
    }

    public static int getLoginID(Connection conn, String login) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Osoby_Numer_Identyfikacyjny FROM Dane_Do_Logowania WHERE Login = ?");
        stmt.setString(1, login);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            int logNum = rSet.getInt(1);
            rSet.close();
            stmt.close();
            return logNum;
        }
        else{
            rSet.close();
            stmt.close();
            return 0;
        }
    }

    public static int checkClientLoginDetails(Connection conn, String login, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Klienci_Numer_Klienta FROM Dane_Do_Logowania_Klienci WHERE Login = ?");
        stmt.setString(1, login);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            int logNum = rSet.getInt(1);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT RN FROM (SELECT Haslo AS HA, ROWNUM AS RN FROM Generator_Passwords_Clients) WHERE HA = ?");
            stmt2.setString(1, password);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                int passNum = rSet2.getInt(1);
                if(passNum == logNum){
                    return 0;
                }
            }
            rSet2.close();
            stmt2.close();
            rSet.close();
            stmt.close();
            return 42;
        }
        else{
            rSet.close();
            stmt.close();
            return 42;
        }
    }

    public static int getClientLoginID(Connection conn, String login) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Klienci_Numer_Klienta FROM Dane_Do_Logowania_Klienci WHERE Login = ?");
        stmt.setString(1, login);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            int logNum = rSet.getInt(1);
            rSet.close();
            stmt.close();
            return logNum;
        }
        else{
            rSet.close();
            stmt.close();
            return 0;
        }
    }

    public static void deleteLoginDetails(Connection conn, int id) throws SQLException {
        String login;
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("SELECT Login FROM Dane_Do_Logowania WHERE Osoby_Numer_Identyfikacyjny = ?");
            stmt.setInt(1, id);
            ResultSet rSet = stmt.executeQuery();

            if(rSet.next()){
                login = Integer.toString(rSet.getString(1).hashCode());
                rSet.close();
                stmt.close();
            }
            else{
                rSet.close();
                stmt.close();
                return;
            }

            stmt=conn.prepareStatement("UPDATE Dane_Do_Logowania SET Login = ? WHERE Osoby_Numer_Identyfikacyjny = ?");

            stmt.setString(1, login);
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

    public static void deleteClientLoginDetails(Connection conn, int id) throws SQLException {
        String login;
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("SELECT Login FROM Dane_Do_Logowania_Klienci WHERE Klienci_Numer_Klienta = ?");
            stmt.setInt(1, id);
            ResultSet rSet = stmt.executeQuery();

            if(rSet.next()){
                login = Integer.toString(rSet.getString(1).hashCode());
                rSet.close();
                stmt.close();
            }
            else{
                rSet.close();
                stmt.close();
                return;
            }

            stmt=conn.prepareStatement("UPDATE Dane_Do_Logowania_Klienci SET Login = ? WHERE Klienci_Numer_Klienta = ?");

            stmt.setString(1, login);
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
}
