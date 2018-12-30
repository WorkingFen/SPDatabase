package JDBC;

import java.sql.*;

public class JDBC {

    private final Connection conn;

    private static final String host = "@localhost";
    private static final String port = ":1521";
    private static final String baseName = "/pdborcl";
    private static final String url = "jdbc:oracle:thin:"+host+port+baseName;
    private static final String username = "FEN";
    private static final String password = "smiley";

    public Connection getConn() {
        return conn;
    }

    public JDBC() throws SQLException {
        this.conn = DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        JDBC test;
        try {
            test = new JDBC();
            //LoginDetails.checkLoginDetails(test.getConn(), "Buzzard9812", "1979517");
            //System.out.print("Wpisz nowy login: \n");
            //String login = System.console().readLine();
            //String login = "login";
            //test.insertLogin(login);
        } catch (SQLException ex) {
            System.out.println("Error JavaAppJDBC: " + ex.getMessage());
        }
    }

}