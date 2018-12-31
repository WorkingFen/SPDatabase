package JDBC;

import sample.AuditorInspection;
import sample.RepairmanInspection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inspection {

    private void addInspection(Connection conn, String date, int poolID) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Przeglady VALUES(?, to_date(?, 'YY/MM/DD'), ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Przeglady.Numer_Przegladu) FROM Przeglady");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, date);
            stmt.setInt(3, poolID);
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

    static public AuditorInspection getAuditorInspection(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT p.Data, p.Baseny_Numer_Obiektu FROM Przeglady p WHERE Numer_Przegladu = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            int noObject = rSet.getInt(2);
            PreparedStatement stmt2 = conn.prepareStatement("SELECT b.Nazwa_Obiektu, b.Miasto FROM Baseny b WHERE Numer_Obiektu = ?");
            stmt2.setInt(1, noObject);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                String objectName = rSet2.getString(1) + ", " + rSet2.getString(2);
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return new AuditorInspection(id, objectName, date);
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

    static public RepairmanInspection getRepairmanInspection(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT p.Data FROM Przeglady p WHERE Numer_Przegladu = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            rSet.close();
            stmt.close();
            return new RepairmanInspection(id, date, "BRAK");
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
