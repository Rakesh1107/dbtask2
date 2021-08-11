package db;

import logic.Account;
import logic.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {
    static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb?autoReconnect=true&useSSL=false", "root", "1234");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customers");
            while (resultSet.next()) {
                Customer customer = new Customer();
                int userId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                long mobileNumber = resultSet.getLong(3);
                String address = resultSet.getString(4);

                customer.setName(name);
                customer.setUserId(userId);
                customer.setMobileNumber(mobileNumber);
                customer.setAddress(address);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from accounts");
            while (resultSet.next()) {
                Account account;
                account = new Account();
                int userId = resultSet.getInt(2);
                long accountNumber = resultSet.getLong(3);
                long balance = resultSet.getLong(4);
                String branch = resultSet.getString(5);

                account.setUserId(userId);
                account.setAccountNumber(accountNumber);
                account.setBalance(balance);
                account.setBranch(branch);

                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static void insertIntoAccounts(Account account) {
        try {
            String query = "insert into accounts (userid, acc_no, balance, branch) values (?,?,?,?)";
            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setLong(2, account.getAccountNumber());
            preparedStatement.setLong(3, account.getBalance());
            preparedStatement.setString(4, account.getBranch());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoCustomers(Customer customer) {
        try {
            String query = "insert into customers values (?,?,?,?)";
            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customer.getUserId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setLong(3, customer.getMobileNumber());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoAccounts(List<Account> accounts) {
        try {
            String query = "insert into accounts (userid, acc_no, balance, branch) values (?,?,?,?)";
            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
            for (Account account : accounts) {
                preparedStatement.setInt(1, account.getUserId());
                preparedStatement.setLong(2, account.getAccountNumber());
                preparedStatement.setLong(3, account.getBalance());
                preparedStatement.setString(4, account.getBranch());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoCustomers(List<Customer> customers) {
        try {
            String query = "insert into customers values (?,?,?,?)";
            for (Customer customer : customers) {
                PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, customer.getUserId());
                preparedStatement.setString(2, customer.getName());
                preparedStatement.setLong(3, customer.getMobileNumber());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
