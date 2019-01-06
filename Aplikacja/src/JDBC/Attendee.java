package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Attendee {

    public static void addAttendee(Connection conn, int lessonID, int clientID) throws SQLException {
        int noAttendees;
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Uczestnicy_Lekcji VALUES(?, ?)");

            stmt.setInt(1, clientID);
            stmt.setInt(2, lessonID);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt = conn.prepareStatement("SELECT Liczba_Zapisanych_Osob FROM Lekcje_Plywania WHERE Numer_Lekcji = ?");
            stmt.setInt(1, lessonID);
            ResultSet rSet = stmt.executeQuery();
            if(rSet.next()){
                noAttendees = rSet.getInt(1)+1;
                rSet.close();
                stmt.close();
            }
            else{
                rSet.close();
                stmt.close();
                return;
            }

            stmt = conn.prepareStatement("UPDATE Lekcje_Plywania SET Liczba_Zapisanych_Osob = ? WHERE Numer_Lekcji = ?");
            stmt.setInt(1, noAttendees);
            stmt.setInt(2, lessonID);
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

    public static void deleteAttendee(Connection conn, int lessonID, int clientID) throws SQLException {
        int noAttendees;
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("SELECT Liczba_Zapisanych_Osob FROM Lekcje_Plywania WHERE Numer_Lekcji = ?");
            stmt.setInt(1, lessonID);
            ResultSet rSet = stmt.executeQuery();
            if(rSet.next()){
                noAttendees = rSet.getInt(1)-1;
                rSet.close();
                stmt.close();
            }
            else{
                rSet.close();
                stmt.close();
                return;
            }

            stmt = conn.prepareStatement("UPDATE Lekcje_Plywania SET Liczba_Zapisanych_Osob = ? WHERE Numer_Lekcji = ?");
            stmt.setInt(1, noAttendees);
            stmt.setInt(2, lessonID);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt = conn.prepareStatement("DELETE FROM Uczestnicy_Lekcji WHERE Klienci_Numer_Klienta = ?");

            stmt.setInt(1, clientID);
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
