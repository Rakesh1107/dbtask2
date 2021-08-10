package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Result {

    public static ResultSet getAccounts() throws SQLException {
        Statement statement = Query.getStatement(Connector.getConnection());
        return statement.executeQuery("select * from accounts");
    }

    public static ResultSet getCustomers() throws SQLException {
        Statement statement = Query.getStatement(Connector.getConnection());
        return statement.executeQuery("select * from customers");
    }

}
