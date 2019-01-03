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
        String date;
        String noAttendees;
        int noPool;
        String surname;
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT Data_i_Godzina, Liczba_zapisanych_osob, Nazwisko FROM " +
                        "(SELECT * FROM Lekcje_Plywania JOIN Pracownicy ON Numer_Ratownika = Numer_Identyfikacyjny) " +
                        "WHERE Numer_Lekcji = ? AND Data_I_Godzina > SYSDATE");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            date = rSet.getString(1);
            noAttendees = rSet.getString(2)+"/6";
            surname = rSet.getString(3);
            rSet.close();
            stmt.close();
            return new CashierLesson(date, noAttendees, surname, msg);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public ClientLesson getClientLesson(Connection conn, String msg, int id, String poolItem) throws SQLException {
        String date;
        String noAttendees;
        String surname;
        PreparedStatement stmt = conn.prepareStatement("SELECT Data_i_Godzina, Liczba_Zapisanych_Osob, Nazwisko FROM" +
                "((SELECT Data_i_Godzina, Liczba_Zapisanych_Osob, Nazwisko, Baseny_Numer_Obiektu FROM " +
                "(SELECT lp.Data_i_Godzina, lp.Numer_Lekcji, lp.Liczba_Zapisanych_Osob, p.Nazwisko, p.Baseny_Numer_Obiektu FROM Lekcje_Plywania lp JOIN Pracownicy p ON lp.Numer_Ratownika = p.Numer_Identyfikacyjny) " +
                "WHERE Numer_Lekcji = ? AND Liczba_zapisanych_osob < 6 AND Data_I_Godzina > SYSDATE) a JOIN Baseny b ON a.Baseny_Numer_Obiektu = b.Numer_Obiektu)" +
                "WHERE Nazwa_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setString(2, poolItem);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            date = rSet.getString(1);
            noAttendees = rSet.getString(2)+"/6";
            surname = rSet.getString(3);
            rSet.close();
            stmt.close();
            return new ClientLesson(date, noAttendees, surname, msg);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
