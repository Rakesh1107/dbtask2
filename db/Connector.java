package db;

import exception.BankException;
import pojo.Account;
import pojo.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {

    static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/bankdb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "1234";

    public static Connection getConnection() throws BankException {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            throw new BankException("Connecting to database failed");
        }
        return connection;
    }

    public static List<Customer> getCustomers() throws BankException {
        List<Customer> customers = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            String selectQuery = "select * from customers";

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    long mobileNumber = resultSet.getLong(3);
                    String address = resultSet.getString(4);

                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setUserId(userId);
                    customer.setMobileNumber(mobileNumber);
                    customer.setAddress(address);

                    customers.add(customer);
                }
                return customers;
            }
        } catch (SQLException e) {
            throw new BankException("No records found");
        }
    }

    public static List<Account> getAccounts() throws BankException {
        List<Account> accounts = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            String selectQuery = "select * from accounts";
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {

                    int userId = resultSet.getInt(1);
                    long accountNumber = resultSet.getLong(2);
                    long balance = resultSet.getLong(3);
                    String branch = resultSet.getString(4);
                    Account account = new Account();

                    account.setUserId(userId);
                    account.setAccountNumber(accountNumber);
                    account.setBalance(balance);
                    account.setBranch(branch);

                    accounts.add(account);
                }
                return accounts;
            }
        } catch (SQLException e) {
            throw new BankException("No records found");
        }
    }

    public static long insertIntoAccounts(Account account) throws BankException {
        String query = "insert into accounts (userid, balance, branch) values (?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setLong(2, account.getBalance());
            preparedStatement.setString(3, account.getBranch());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new BankException("Adding account failed");
        }
        return -1;
    }

    public static int insertIntoCustomers(Customer customer) throws BankException {
        String query = "insert into customers (name, mobile, address) values (?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setLong(2, customer.getMobileNumber());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new BankException("Adding user failed");
        }
        return -1;
    }


    public static List<Integer> insertIntoAccounts(List<Account> accounts) throws BankException {
        String query = "insert into accounts (userid, balance, branch) values (?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            List<Integer> list = new ArrayList<>();
            for (Account account : accounts) {
                preparedStatement.setInt(1, account.getUserId());
                preparedStatement.setLong(2, account.getBalance());
                preparedStatement.setString(3, account.getBranch());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    list.add(resultSet.getInt(1));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new BankException("Adding accounts failed");
        }
    }

    public static List<Integer> insertIntoCustomers(List<Customer> customers) throws BankException {
        String query = "insert into customers (name, mobile, address) values (?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            List<Integer> list = new ArrayList<>();
            for (Customer customer : customers) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setLong(2, customer.getMobileNumber());
                preparedStatement.setString(3, customer.getAddress());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    list.add(resultSet.getInt(1));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new BankException("Adding users failed");
        }
    }
}
