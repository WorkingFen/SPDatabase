package JDBC;

import sample.Auditor.AuditorTransaction;
import sample.Manager.ManagerIncome;
import sample.Manager.ManagerSalary;
import sample.Manager.ManagerTransaction;
import sample.Marketing.MarketingTransaction;
import sample.Owner.OwnerIncome;

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

    static public ManagerIncome getManagerIncome(Connection conn, String year, int month) throws SQLException {
        String date;
        if(month < 10) date = year+"-0"+month;
        else date = year+"-"+month;

        int income;
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT sum(Ilosc*Cena) FROM " +
                        "(SELECT a.Data, a.Ilosc, u.Cena, u.Baseny_Numer_Obiektu FROM " +
                        "(SELECT t.Data, k.Ilosc, k.Uslugi_Numer_Uslugi FROM Transakcje t JOIN Koszyki k ON t.Numer_Transakcji = k.Transakcje_Numer_Transakcji) a " +
                        "JOIN Uslugi u ON a.Uslugi_Numer_Uslugi = u.Numer_Uslugi) " +
                        "WHERE to_char(Data, 'YYYY-MM') = ?"
        );
        stmt.setString(1, date);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            income = rSet.getInt(1);
            rSet.close();
            stmt.close();
            return new ManagerIncome(date, income);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public ManagerSalary getManagerSalary(Connection conn, String year, int month) throws SQLException {
        String date;
        if(month < 10) date = year+"-0"+month;
        else date = year+"-"+month;

        int expenses;

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT sum(Wynagrodzenie+Dodatek_Do_Pensji) FROM " +
                        "(SELECT p.Dodatek_Do_Pensji, s.Wynagrodzenie FROM Pracownicy p JOIN Stanowiska s ON p.Stanowiska_Numer_Stanowiska = s.Numer_Stanowiska) "
        );
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            expenses = rSet.getInt(1);
            rSet.close();
            stmt.close();
            return new ManagerSalary(date, expenses);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public OwnerIncome getOwnerIncome(Connection conn, String year, int month, String poolName) throws SQLException {
        String date;
        if(month < 10) date = year+"-0"+month;
        else date = year+"-"+month;

        int income;
        int expenses;
        int noPool;
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT Numer_Obiektu FROM " +
                        "(SELECT b.Numer_Obiektu, b.Nazwa_Obiektu FROM " +
                            "(SELECT u.Baseny_Numer_Obiektu FROM " +
                                "(SELECT k.Uslugi_Numer_Uslugi FROM Transakcje t JOIN Koszyki k ON t.Numer_Transakcji = k.Transakcje_Numer_Transakcji) a " +
                            "JOIN Uslugi u ON a.Uslugi_Numer_Uslugi = u.Numer_Uslugi) z " +
                        "JOIN Baseny b ON z.Baseny_Numer_Obiektu = b.Numer_Obiektu) " +
                     "WHERE Nazwa_Obiektu = ?"
        );
        stmt.setString(1, poolName);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            noPool = rSet.getInt(1);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement(
                "SELECT sum(Ilosc*Cena) FROM " +
                        "(SELECT a.Data, a.Ilosc, u.Cena, u.Baseny_Numer_Obiektu FROM " +
                            "(SELECT t.Data, k.Ilosc, k.Uslugi_Numer_Uslugi FROM Transakcje t JOIN Koszyki k ON t.Numer_Transakcji = k.Transakcje_Numer_Transakcji) a " +
                        "JOIN Uslugi u ON a.Uslugi_Numer_Uslugi = u.Numer_Uslugi) " +
                    "WHERE Baseny_Numer_Obiektu = ? AND to_char(Data, 'YYYY-MM') = ?"
        );
        stmt.setInt(1, noPool);
        stmt.setString(2, date);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            income = rSet.getInt(1);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement(
                "SELECT sum(Wynagrodzenie+Dodatek_Do_Pensji) FROM " +
                        "(SELECT p.Baseny_Numer_Obiektu, p.Dodatek_Do_Pensji, s.Wynagrodzenie FROM Pracownicy p JOIN Stanowiska s ON p.Stanowiska_Numer_Stanowiska = s.Numer_Stanowiska) " +
                    "WHERE Baseny_Numer_Obiektu = ?"
        );
        stmt.setInt(1, noPool);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            expenses = rSet.getInt(1);
            rSet.close();
            stmt.close();
            return new OwnerIncome(date, income, expenses);
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }

    static public MarketingTransaction getMarketingTransaction(Connection conn, int id, String poolItem) throws SQLException {
        String date;
        String name;
        String surname;
        String transactionType;
        int value;
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT to_char(Data, 'YYYY-MM-DD HH24:MI:SS'), (Ilosc*Cena), Nazwa_Uslugi FROM " +
                        "(SELECT z.Numer_Transakcji, z.Data, z.Ilosc, z.Cena, z.Nazwa_Uslugi, b.Nazwa_Obiektu FROM " +
                            "(SELECT a.Numer_Transakcji, a.Data, a.Ilosc, u.Cena, u.Baseny_Numer_Obiektu, u.Nazwa_Uslugi FROM " +
                                "(SELECT t.Numer_Transakcji, t.Data, k.Uslugi_Numer_Uslugi, k.Ilosc FROM Koszyki k JOIN Transakcje t ON t.Numer_Transakcji = k.Transakcje_Numer_Transakcji) a " +
                            "JOIN Uslugi u ON a.Uslugi_Numer_Uslugi = u.Numer_Uslugi) z " +
                        "JOIN Baseny b ON z.Baseny_Numer_Obiektu = b.Numer_Obiektu) " +
                    "WHERE Numer_Transakcji = ? AND Nazwa_Obiektu = ?"
        );
        stmt.setInt(1, id);
        stmt.setString(2, poolItem);
        ResultSet rSet = stmt.executeQuery();
        if(rSet.next()){
            date = rSet.getString(1);
            value = rSet.getInt(2);
            transactionType = rSet.getString(3);
            rSet.close();
            stmt.close();
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
        stmt = conn.prepareStatement(
                "SELECT Ogolne_Numer_Uslugi FROM " +
                        "(SELECT k.Transakcje_Numer_Transakcji, u.Ogolne_Numer_Uslugi FROM Koszyki k JOIN Uslugi u ON k.Uslugi_Numer_Uslugi = u.Numer_Uslugi) a " +
                    "WHERE Transakcje_Numer_Transakcji = ?"
        );
        stmt.setInt(1, id);
        rSet = stmt.executeQuery();
        if(rSet.next()){
            int noService = rSet.getInt(1);
            if(noService != 0) {
                PreparedStatement stmt2 = conn.prepareStatement(
                        "SELECT Imie, Nazwisko FROM " +
                                "(SELECT za.Imie, za.Nazwisko, za.Ogolne_Numer_Uslugi, za.Nazwa_Obiektu, k.Transakcje_Numer_Transakcji FROM " +
                                    "(SELECT az.Imie, az.Nazwisko, az.Ogolne_Numer_Uslugi, az.Numer_Uslugi, b.Nazwa_Obiektu FROM " +
                                        "(SELECT z.Imie, z.Nazwisko, z.Ogolne_Numer_Uslugi, u.Baseny_Numer_Obiektu, u.Numer_Uslugi FROM " +
                                            "(SELECT a.Imie, a.Nazwisko, lp.Ogolne_Numer_Uslugi FROM " +
                                                "(SELECT kl.Imie, kl.Nazwisko, ul.Lekcje_Plywania_Numer_Lekcji FROM Klienci kl JOIN Uczestnicy_Lekcji ul ON kl.Numer_Klienta = ul.Klienci_Numer_Klienta) a " +
                                            "JOIN Lekcje_Plywania lp ON a.Lekcje_Plywania_Numer_Lekcji = lp.Numer_Lekcji) z " +
                                        "JOIN Uslugi u ON z.Ogolne_Numer_Uslugi = u.Ogolne_Numer_Uslugi) az " +
                                    "JOIN Baseny b ON az.Baseny_Numer_Obiektu = b.Numer_Obiektu) za " +
                                "JOIN Koszyki k ON k.Uslugi_Numer_Uslugi = za.Numer_Uslugi) " +
                            "WHERE Transakcje_Numer_Transakcji = ? AND Nazwa_Obiektu = ? AND Ogolne_Numer_Uslugi = ?"
                );
                stmt2.setInt(1, id);
                stmt2.setString(2, poolItem);
                stmt2.setInt(3, noService);
                ResultSet rSet2 = stmt2.executeQuery();
                if (rSet2.next()) {
                    name = rSet2.getString(1);
                    surname = rSet2.getString(2);
                    rSet2.close();
                    stmt2.close();
                    rSet.close();
                    stmt.close();
                    return new MarketingTransaction(date, value, transactionType, name, surname);
                }
                rSet2.close();
                stmt2.close();

                stmt2 = conn.prepareStatement(
                        "SELECT Imie, Nazwisko FROM " +
                                "(SELECT az.Transakcje_Numer_Transakcji, az.Ogolne_Numer_Uslugi, az.Nazwa_Obiektu, kl.Imie, kl.Nazwisko FROM " +
                                    "(SELECT z.Transakcje_Numer_Transakcji, z.Ogolne_Numer_Uslugi, z.Nazwa_Obiektu, rt.Klienci_Numer_Klienta FROM " +
                                        "(SELECT a.Transakcje_Numer_Transakcji, a.Ogolne_Numer_Uslugi, b.Nazwa_Obiektu FROM " +
                                            "(SELECT k.Transakcje_Numer_Transakcji, u.Baseny_Numer_Obiektu, u.Ogolne_Numer_Uslugi FROM Koszyki k JOIN Uslugi u ON k.Uslugi_Numer_Uslugi = u.Numer_Uslugi) a " +
                                        "JOIN Baseny b ON a.Baseny_Numer_Obiektu = b.Numer_Obiektu) z " +
                                    "JOIN Rezerwacje_Toru rt ON z.Ogolne_Numer_Uslugi = rt.Ogolne_Numer_Uslugi) az " +
                                "JOIN Klienci kl ON kl.Numer_Klienta = az.Klienci_Numer_Klienta) " +
                            "WHERE Transakcje_Numer_Transakcji = ? AND Nazwa_Obiektu = ? AND Ogolne_Numer_Uslugi = ?"
                );
                stmt2.setInt(1, id);
                stmt2.setString(2, poolItem);
                stmt2.setInt(3, noService);
                rSet2 = stmt2.executeQuery();
                if (rSet2.next()) {
                    name = rSet2.getString(1);
                    surname = rSet2.getString(2);
                    rSet2.close();
                    stmt2.close();
                    rSet.close();
                    stmt.close();
                    return new MarketingTransaction(date, value, transactionType, name, surname);
                } else {
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
                return new MarketingTransaction(date, value, transactionType,"-", "-");
            }
        }
        else{
            rSet.close();
            stmt.close();
            return null;
        }
    }
}
