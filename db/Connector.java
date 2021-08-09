package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb?autoReconnect=true&useSSL=false", "root", "1234");
        }
        return connection;
    }
}
