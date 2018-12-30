package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cart {

    private void addCart(Connection conn, int transactionID, int serviceID, int amount) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Koszyki VALUES(?, ?, ?)");

            stmt.setInt(1, transactionID);
            stmt.setInt(2, serviceID);
            stmt.setInt(3, amount);
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
}
