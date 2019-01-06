package JDBC;

import sample.Cashier.CashierClient;
import sample.Cashier.CashierController;
import sample.Marketing.MarketingClient;
import sample.Marketing.MarketingTopClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

    static public int addClient(Connection conn, String name, String surname, String phone, String email) throws SQLException {
        int id;
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Klienci VALUES(?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Klienci.Numer_klienta) FROM Klienci");
            ResultSet rset = stmt2.executeQuery();

            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, phone);
            stmt.setString(5, email);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            return id;
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            conn.rollback();
        }
        finally {
            conn.setAutoCommit(true);
        }
        return 0;
    }

    static public int getClient(Connection conn, String name, String surname, String phone) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Numer_Klienta FROM Klienci WHERE Imie = ? AND Nazwisko = ? AND Numer_Telefonu = ?");
        stmt.setString(1, name);
        stmt.setString(2, surname);
        stmt.setString(3, phone);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            int id = rSet.getInt(1);
            rSet.close();
            stmt.close();
            return id;
        }
        else{
            rSet.close();
            stmt.close();
            return 0;
        }
    }

    static public void editClient(Connection conn, int number, String name, String surname, String phone, String email) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            if(name != null && surname != null && phone != null){
                stmt=conn.prepareStatement("UPDATE Klienci SET Imie = ?, Nazwisko = ?, Numer_Telefonu = ? WHERE Numer_Klienta = ?");

                stmt.setString(1, name);
                stmt.setString(2, surname);
                stmt.setString(3, phone);
                stmt.setInt(4, number);
                stmt.executeQuery();
                stmt.close();
                conn.commit();
            }
            else return;
            stmt=conn.prepareStatement("UPDATE Klienci SET \"Adres_e-mail\" = ? WHERE Numer_Klienta = ?");

            stmt.setString(1, email);
            stmt.setInt(2, number);
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

    static public void deleteClient(Connection conn, String name, String surname, String email) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("UPDATE Klienci SET Imie = 'Person', Nazwisko = 'Deleted', Numer_Telefonu = '0', \"Adres_e-mail\" = null WHERE Imie = ? AND Nazwisko = ? AND \"Adres_e-mail\" = ?");

            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, email);
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

    static public CashierClient getCashierClient(CashierController cc, Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT k.Imie, k.Nazwisko, k.Numer_Telefonu, k.\"Adres_e-mail\" FROM Klienci k WHERE Numer_Klienta = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            String phone = rSet.getString(3);
            String email = rSet.getString(4);
            rSet.close();
            stmt.close();
            if(name.equals("Person") && surname.equals("Deleted") && phone.equals("0") && email == null) return null;
            return new CashierClient(cc, id, name, surname, phone, email);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public MarketingClient getMarketingClient(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Imie, Nazwisko FROM Klienci WHERE Numer_Klienta = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            String name = rSet.getString(1);
            String surname = rSet.getString(2);
            rSet.close();
            stmt.close();
            return new MarketingClient(id, name, surname, "Yyyyy");
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public MarketingTopClient getMarketingTopClient(Connection conn, int id) throws SQLException {
        String name;
        String surname;
        int noTransactions;
        PreparedStatement stmt = conn.prepareStatement("SELECT Imie, Nazwisko FROM Klienci WHERE Numer_Klienta = ?");
        stmt.setInt(1, id);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            name = rSet.getString(1);
            surname = rSet.getString(2);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement(
                "SELECT COUNT(Lekcje_Plywania_Numer_Lekcji) FROM " +
                        "(SELECT k.Numer_Klienta, ul.Lekcje_Plywania_Numer_Lekcji FROM Klienci k JOIN Uczestnicy_Lekcji ul ON k.Numer_Klienta = ul.Klienci_Numer_Klienta) " +
                    "WHERE Numer_Klienta = ?"
        );
        stmt.setInt(1, id);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            noTransactions = rSet.getInt(1);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement(
                "SELECT COUNT(Numer_Rezerwacji) FROM " +
                        "(SELECT k.Numer_Klienta, r.Numer_Rezerwacji FROM Klienci k JOIN Rezerwacje_Toru r ON k.Numer_Klienta = r.Klienci_Numer_Klienta) " +
                    "WHERE Numer_Klienta = ?"
        );
        stmt.setInt(1, id);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            noTransactions += rSet.getInt(1);
            rSet.close();
            stmt.close();
            if(noTransactions < 3) return null;
            return new MarketingTopClient(id, name, surname, noTransactions);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
