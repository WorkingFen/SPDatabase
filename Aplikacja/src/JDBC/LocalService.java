package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalService {

    private void addLocalService(Connection conn, String name, int price, int poolID) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Lokalne VALUES(?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Lokalne.Numer_uslugi) FROM Lokalne");
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

            stmt=conn.prepareStatement("INSERT INTO Uslugi VALUES(?, ?, ?, ?, NULL, ?)");

            stmt2 = conn.prepareStatement("SELECT MAX(Uslugi.Numer_uslugi) FROM Uslugi");
            rset = stmt2.executeQuery();

            int serviceID;
            if(rset.next()) serviceID = rset.getInt(1)+1;
            else serviceID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, serviceID);
            stmt.setString(2, name);
            stmt.setInt(3, price);
            stmt.setInt(4, poolID);
            stmt.setInt(5, id);
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
