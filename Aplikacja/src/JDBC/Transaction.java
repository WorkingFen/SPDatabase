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

            stmt = conn.prepareStatement("INSERT INTO Transakcje VALUES(?, SYSDATE)");

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

    static public AuditorTransaction getAuditorTransaction(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT t.Data FROM Transakcje t WHERE Numer_transakcji = ?");
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
        stmt = conn.prepareStatement("SELECT Nazwa_Uslugi, Cena FROM Uslugi WHERE Numer_Uslugi = ?");
        stmt.setInt(1, noService);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            String serviceName = rSet.getString(1);
            int price = rSet.getInt(2)*amount;
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
        PreparedStatement stmt = conn.prepareStatement("SELECT t.Data FROM Transakcje t WHERE Numer_transakcji = ?");
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
