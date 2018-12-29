import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {

    private final Connection conn;

    private static final String host = "@localhost";
    private static final String port = ":1521";
    private static final String baseName = "/pdborcl";
    private static final String url = "jdbc:oracle:thin:"+host+port+baseName;
    private static final String username = "FEN";
    private static final String password = "smiley";

    public JDBC() throws SQLException {
        this.conn = DriverManager.getConnection(url, username, password);
    }

    private void addAuditor() throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Audytorzy VALUES(?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Audytorzy.Numer_Identyfikacyjny) FROM Audytorzy");
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

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, ?, NULL, NULL)");

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rset = stmt2.executeQuery();

            int personID;
            if(rset.next()) personID = rset.getInt(1)+1;
            else personID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, personID);
            stmt.setInt(2, id);
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

    private void addOwner(String name, String surname) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Wlasciciele VALUES(?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Wlasciciele.Numer_Identyfikacyjny) FROM Wlasciciele");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, NULL, ?, NULL)");

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rset = stmt2.executeQuery();

            int personID;
            if(rset.next()) personID = rset.getInt(1)+1;
            else personID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, personID);
            stmt.setInt(2, id);
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

    private void addEmployee(String name, String surname, int perk, int postID, int poolID) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Pracownicy VALUES(?, ?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Pracownicy.Numer_Identyfikacyjny) FROM Pracownicy");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setInt(4, perk);
            stmt.setInt(5, postID);
            stmt.setInt(6, poolID);
            stmt.executeQuery();
            stmt.close();
            conn.commit();

            stmt=conn.prepareStatement("INSERT INTO Osoby VALUES(?, NULL, NULL, ?)");

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rset = stmt2.executeQuery();

            int personID;
            if(rset.next()) personID = rset.getInt(1)+1;
            else personID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, personID);
            stmt.setInt(2, id);
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

    /*private void addLoginDetails(String login, String password) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Dane_Do_Logowania VALUES(?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Dane_Do_Logowania.Id) FROM Dane_Do_Logowania");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt2 = conn.prepareStatement("SELECT MAX(Osoby.Numer_Identyfikacyjny) FROM Osoby");
            rset = stmt2.executeQuery();

            int personID;
            if(rset.next()) personID = rset.getInt(1)+1;
            else personID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setInt(4, personID);
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
    }*/

    private void addPost(String name, int salary) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Stanowiska VALUES(?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Stanowiska.Numer_Stanowiska) FROM Stanowiska");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, salary);
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

    private void addPool(String name, int noPath, String city, String street) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Baseny VALUES(?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Baseny.Numer_Obiektu) FROM Baseny");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, noPath);
            stmt.setString(4, city);
            stmt.setString(5, street);
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

    private void addInspection(String date, int poolID) throws SQLException {
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

    private void addLocalService(String name, int price, int poolID) throws SQLException {
        try{
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO Lokalne VALUES(?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Lokalne.Numer_uslugi) FROM Lokalne");
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

            stmt=conn.prepareStatement("INSERT INTO Uslugi VALUES(?, ?, ?, ?, NULL, ?)");

            stmt2 = conn.prepareStatement("SELECT MAX(Uslugi.Numer_uslugi) FROM Uslugi");
            rset = stmt2.executeQuery();

            int serviceID;
            if(rset.next()) serviceID = rset.getInt(1)+1;
            else serviceID = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, serviceID);
            stmt.setString(2, name);
            stmt.setInt(3, price);
            stmt.setInt(4, poolID);
            stmt.setInt(5, id);
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

    private void addGeneralService(String name, int price) throws SQLException {
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

    private void addCart(int transactionID, int serviceID, int amount) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Koszyki VALUES(?, ?, ?)");

            stmt.setInt(1, transactionID);
            stmt.setInt(2, serviceID);
            stmt.setInt(3, amount);
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

    private void addTransaction() throws SQLException {
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

    private void addLesson(String date, int noGuard, int generalID) throws SQLException {
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

    private void addReservation(String date, int noPath, String status, int clientID, int generalID) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Rezerwacje_Toru VALUES(?, to_date(?, 'YY/MM/DD HH24:MM'), ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Rezerwacje_Toru.Numer_rezerwacji) FROM Rezerwacje_Toru");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, date);
            stmt.setInt(3, noPath);
            stmt.setString(4, status);
            stmt.setInt(5, clientID);
            stmt.setInt(6, generalID);
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

    private void addAttendee(int clientID, int lessonID) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Uczestnicy_Lekcji VALUES(?, ?)");

            stmt.setInt(1, clientID);
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

    private void addClient(String name, String surname, String telNumber, String email) throws SQLException {
        try {
            PreparedStatement stmt;

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("INSERT INTO Klienci VALUES(?, ?, ?, ?, ?)");

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(Klienci.Numer_klienta) FROM Klienci");
            ResultSet rset = stmt2.executeQuery();

            int id;
            if(rset.next()) id = rset.getInt(1)+1;
            else id = 1;

            rset.close();
            stmt2.close();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, telNumber);
            stmt.setString(5, email);
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

    public static void main(String[] args) {
        JDBC test;
        try {
            test = new JDBC();
            //System.out.print("Wpisz nowy login: \n");
            //String login = System.console().readLine();
            //String login = "login";
            //test.insertLogin(login);
            //test.addEmployee("Karol", "Rzepag", 0, 7, 1);
        } catch (SQLException ex) {
            System.out.println("Error JavaAppJDBC: " + ex.getMessage());
        }
    }

}