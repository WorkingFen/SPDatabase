package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pool {

    private void addPool(Connection conn, String name, int noPath, String city, String street) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Baseny VALUES(?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Baseny.Numer_Obiektu) FROM Baseny");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, noPath);
            stmt.setString(4, city);
            stmt.setString(5, street);
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

    static public String getClientPool(Connection conn, int id) throws SQLException {
        String poolName;
        PreparedStatement stmt = conn.prepareStatement("SELECT Nazwa_Obiektu, Miasto FROM Baseny WHERE Numer_Obiektu = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            poolName = rSet.getString(1) +", "+ rSet.getString(2);
            rSet.close();
            stmt.close();
            return poolName;
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
