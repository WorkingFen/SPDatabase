package JDBC;

import sample.AuditorTransaction;
import sample.ManagerTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {

    private void addTransaction(Connection conn) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Transakcje VALUES(?, to_date(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Transakcje.Numer_transakcji) FROM Transakcje");
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
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            conn.rollback();
        }
        finally {
            conn.setAutoCommit(true);
        }
    }

    static public AuditorTransaction getAuditorTransaction(Connection conn, int id, String poolName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT to_char(Data, 'YYYY-MM-DD HH24:MI:SS'), Nazwa_Uslugi, (Ilosc*Cena) FROM " +
                "(SELECT z.Numer_Transakcji, z.Data, z.Ilosc, z.Nazwa_Uslugi, z.Cena, b.Nazwa_Obiektu FROM " +
                "(SELECT a.Numer_Transakcji, a.Data, a.Ilosc, u.Nazwa_Uslugi, u.Cena, u.Baseny_Numer_Obiektu FROM " +
                "(SELECT t.Numer_Transakcji, t.Data, k.Ilosc, k.Uslugi_Numer_Uslugi FROM " +
                "Transakcje t JOIN Koszyki k ON t.Numer_Transakcji = k.Transakcje_Numer_Transakcji) a " +
                "JOIN Uslugi u ON a.Uslugi_Numer_Uslugi = u.Numer_Uslugi) z " +
                "JOIN Baseny b ON z.Baseny_Numer_Obiektu = b.Numer_Obiektu) " +
                "WHERE Numer_transakcji = ? AND Nazwa_Obiektu = ?");
        stmt.setInt(1, id);
        stmt.setString(2, poolName);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String date = rSet.getString(1);
            String serviceName = rSet.getString(2);
            int price = rSet.getInt(3);
            rSet.close();
            stmt.close();
            return new AuditorTransaction(id, date, serviceName, price);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public ManagerTransaction getManagerTransaction(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT to_char(t.Data, 'YYYY-MM-DD HH24:MI:SS') FROM Transakcje t WHERE Numer_transakcji = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        String date;
        if(rSet.next()){
            date = rSet.getString(1);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement("SELECT Uslugi_Numer_Uslugi, Ilosc FROM Koszyki WHERE Transakcje_Numer_Transakcji = ?");
        stmt.setInt(1, id);
        rSet = stmt.executeQuery();
        int noService;
        int amount;
        if(rSet.next()){
            noService = rSet.getInt(1);
            amount = rSet.getInt(2);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement("SELECT Cena FROM Uslugi WHERE Numer_Uslugi = ?");
        stmt.setInt(1, noService);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            int price = rSet.getInt(1)*amount;
            rSet.close();
            stmt.close();
            return new ManagerTransaction(id, date, price);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
