package db;

import logic.Account;
import logic.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Query {

    public static Statement getStatement() throws SQLException {
        return Connector.getConnection().createStatement();
    }

    public static void insertIntoAccounts(Account account) throws SQLException {
        String query = "insert into accounts (userid, acc_no, balance, branch) values (?,?,?,?)";
        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, account.getUserId());
        preparedStatement.setLong(2, account.getAccountNumber());
        preparedStatement.setLong(3, account.getBalance());
        preparedStatement.setString(4, account.getBranch());
        preparedStatement.executeUpdate();
    }

    public static void insertIntoCustomers(Customer customer) throws SQLException {
        String query = "insert into customers values (?,?,?,?)";
        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, customer.getUserId());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setLong(3, customer.getMobileNumber());
        preparedStatement.setString(4, customer.getAddress());
        preparedStatement.executeUpdate();
    }

    public static void insertIntoAccounts(List<Account> accounts) throws SQLException {
        String query = "insert into accounts (userid, acc_no, balance, branch) values (?,?,?,?)";
        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
        for(Account account: accounts) {
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setLong(2, account.getAccountNumber());
            preparedStatement.setLong(3, account.getBalance());
            preparedStatement.setString(4, account.getBranch());
            preparedStatement.executeUpdate();
        }
    }

    public static void insertIntoCustomers(List<Customer> customers) throws SQLException {
        String query = "insert into customers values (?,?,?,?)";
        for (Customer customer: customers) {
            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customer.getUserId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setLong(3, customer.getMobileNumber());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.executeUpdate();
        }
    }

}
