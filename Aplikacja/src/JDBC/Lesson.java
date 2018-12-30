package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lesson {

    private void addLesson(Connection conn, String date, int noGuard, int generalID) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Lekcje_Plywania VALUES(?, to_date(?, 'YY/MM/DD HH24:MM'), ?, ?, 0)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Lekcje_Plywania.Numer_lekcji) FROM Lekcje_Plywania");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, date);
            stmt.setInt(3, noGuard);
            stmt.setInt(4, generalID);
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
