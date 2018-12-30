package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneralService {

    private void addGeneralService(Connection conn, String name, int price) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("SELECT COUNT(Baseny.Numer_Obiektu) FROM Baseny");
            ResultSet rset = stmt.executeQuery();
            if(rset.next()) {
                PreparedStatement stmt2;
                PreparedStatement stmt3;
                ResultSet rset2;
                int id;
                int serviceID;
                int count = rset.getInt(1);
                for (int i = 0; i < count; i++) {
                    stmt2 = conn.prepareStatement("INSERT INTO Ogolne VALUES(?)");

                    stmt3 = conn.prepareStatement("SELECT MAX(Ogolne.Numer_uslugi) FROM Ogolne");
                    rset2 = stmt3.executeQuery();

                    if (rset2.next()) id = rset2.getInt(1) + 1;
                    else id = 1;

                    rset2.close();
                    stmt3.close();

                    stmt2.setInt(1, id);
                    stmt2.executeQuery();
                    stmt2.close();
                    conn.commit();

                    stmt2 = conn.prepareStatement("INSERT INTO Uslugi VALUES(?, ?, ?, ?, ?, NULL)");

                    stmt3 = conn.prepareStatement("SELECT MAX(Uslugi.Numer_uslugi) FROM Uslugi");
                    rset2 = stmt3.executeQuery();

                    if (rset2.next()) serviceID = rset2.getInt(1) + 1;
                    else serviceID = 1;

                    rset2.close();
                    stmt3.close();

                    stmt2.setInt(1, serviceID);
                    stmt2.setString(2, name);
                    stmt2.setInt(3, price);
                    stmt2.setInt(4, i+1);
                    stmt2.setInt(5, id);
                    stmt2.executeQuery();
                    stmt2.close();
                    conn.commit();
                }
            }
            else
            {
                throw new Exception("Nie ma basenow dla ktorych mozna wykonac operacje");
            }
            rset.close();
            stmt.close();
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
