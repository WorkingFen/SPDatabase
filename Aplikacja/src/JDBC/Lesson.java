package JDBC;

import sample.CashierLesson;
import sample.ClientLesson;
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

    static public CashierLesson getCashierLesson(Connection conn, String msg, int id) throws SQLException {
        String name = "PrObLEmO";
        PreparedStatement stmt = conn.prepareStatement("SELECT lp.Data_i_Godzina, lp.Numer_ratownika, lp.Liczba_zapisanych_osob FROM Lekcje_Plywania lp WHERE Numer_Lekcji = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            int noGuard = rSet.getInt(2);
            String noAttendees = rSet.getString(3)+"/6";
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Nazwisko FROM Pracownicy WHERE Numer_Identyfikacyjny = ?");
            stmt2.setInt(1, noGuard);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                String surname = rSet2.getString(1);
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return new CashierLesson(name, date, noAttendees, surname, msg);
            }
            else{
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return new CashierLesson(name, date, noAttendees, "Informacja na basenie", msg);
            }
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public ClientLesson getClientLesson(Connection conn, String msg, int id) throws SQLException {
        String name = "PrObLEmO";
        PreparedStatement stmt = conn.prepareStatement("SELECT lp.Data_i_Godzina, lp.Numer_ratownika, lp.Liczba_zapisanych_osob FROM Lekcje_Plywania lp WHERE Numer_Lekcji = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            int noGuard = rSet.getInt(2);
            String noAttendees = rSet.getString(3)+"/6";
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Nazwisko FROM Pracownicy WHERE Numer_Identyfikacyjny = ?");
            stmt2.setInt(1, noGuard);
            ResultSet rSet2 = stmt2.executeQuery();
            if(rSet2.next()){
                String surname = rSet2.getString(1);
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return new ClientLesson(name, date, noAttendees, surname, msg);
            }
            else{
                rSet2.close();
                stmt2.close();
                rSet.close();
                stmt.close();
                return new ClientLesson(name, date, noAttendees, "Informacja na basenie", msg);
            }
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
