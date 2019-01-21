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
}