package db;

import pojo.Account;
import pojo.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {
    static Connection connection;

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/bankdb?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "1234";
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static List<Customer> getCustomers() {
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()){
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static long insertIntoAccounts(int userId, String branch) {
        String query = "insert into accounts (userid, balance, branch) values (?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setLong(2, 0);
            preparedStatement.setString(3, branch);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getLong(1);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

//    private static int getAccountNumber() throws SQLException {
//        Statement statement = getConnection().createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM accounts;");
//        return resultSet.getInt(1);
//    }


    public static int insertIntoCustomers(String name, long mobileNumber, String address) {
        String query = "insert into customers (name, mobile, address) values (?,?,?)";
        PreparedStatement preparedStatement = null;

        try  {
            preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, mobileNumber);
            preparedStatement.setString(3, address);
            preparedStatement.executeUpdate();
            try(ResultSet resultSet =  preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    return resultSet.getInt(1);
            }
            } catch (Exception e) {
                return -1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

//    private static int getUserId() throws SQLException {
//        Statement statement = getConnection().createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM CUSTOMERS;");
//        return resultSet.getInt(1);
//    }

    public static boolean insertIntoAccounts(List<Account> accounts) {
        String query = "insert into accounts (userid, balance, branch) values (?,?,?)";
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            for (Account account : accounts) {
                preparedStatement.setInt(1, account.getUserId());
                preparedStatement.setLong(2, account.getBalance());
                preparedStatement.setString(3, account.getBranch());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean insertIntoCustomers(List<Customer> customers) {
        String query = "insert into customers (name, mobile, address) values (?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            for (Customer customer : customers) {
                preparedStatement.setInt(1, customer.getUserId());
                preparedStatement.setString(2, customer.getName());
                preparedStatement.setLong(3, customer.getMobileNumber());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
