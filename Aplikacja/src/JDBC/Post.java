package JDBC;

import sample.HR.HRPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Post {

    private void addPost(Connection conn, String name, int salary) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Stanowiska VALUES(?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Stanowiska.Numer_Stanowiska) FROM Stanowiska");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, salary);
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

    static public HRPost getHRPost(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT s.Nazwa, s.Wynagrodzenie FROM Stanowiska s WHERE Numer_Stanowiska = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            int salary = rSet.getInt(2);
            rSet.close();
            stmt.close();
            return new HRPost(name, salary);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public String getHRPostNames(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Nazwa FROM Stanowiska WHERE Numer_Stanowiska = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            rSet.close();
            stmt.close();
            return name;
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
