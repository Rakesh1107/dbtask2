package db;

import logic.Account;
import logic.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    public static void insertIntoAccounts(Account account) throws SQLException {
        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement("insert into accounts (userid, acc_no, balance, branch) values (?,?,?,?)");
        preparedStatement.setInt(1, account.getUserId());
        preparedStatement.setLong(2, account.getAccountNumber());
        preparedStatement.setLong(3, account.getBalance());
        preparedStatement.setString(4, account.getBranch());
        preparedStatement.executeUpdate();
    }

    public static void insertIntoCustomers(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement("insert into customers values (?,?,?,?)");
        preparedStatement.setInt(1, customer.getUserId());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setLong(3, customer.getMobileNumber());
        preparedStatement.setString(4, customer.getAddress());
        preparedStatement.executeUpdate();
    }

}
