package db;

import logic.Account;
import logic.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Query {

    public static Statement getStatement() throws SQLException {
        return Connector.getConnection().createStatement();
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

    public static void insertIntoAccounts(List<Account> accounts) throws SQLException {
        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement("insert into accounts (userid, acc_no, balance, branch) values (?,?,?,?)");
        for(Account account: accounts) {
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setLong(2, account.getAccountNumber());
            preparedStatement.setLong(3, account.getBalance());
            preparedStatement.setString(4, account.getBranch());
            preparedStatement.executeUpdate();
        }
    }

    public static void insertIntoCustomers(List<Customer> customers) throws SQLException {
        for (Customer customer: customers) {
            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement("insert into customers values (?,?,?,?)");
            preparedStatement.setInt(1, customer.getUserId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setLong(3, customer.getMobileNumber());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.executeUpdate();
        }
    }

}
