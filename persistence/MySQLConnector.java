package persistence;

import pojo.Account;
import pojo.Customer;
import validator.Validator;
import exception.BankException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector implements Persistence {
    static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/bankdb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "1234";

    public MySQLConnector() throws BankException {
        getConnection();
    }

    public static Connection getConnection() throws BankException {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException exception) {
            throw new BankException("Connecting to database failed", exception);
        }
        return connection;
    }

    public static void main(String[] args) throws BankException {
        MySQLConnector connector = new MySQLConnector();

        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setName("Rahul");
        customer1.setMobileNumber(839889389);
        customer1.setAddress("Bengaluru");

        Customer customer2 = new Customer();
        customer2.setName("Ramesh");
        customer2.setMobileNumber(981822891);
        customer2.setAddress("Chennai");

        customers.add(customer1);
        customers.add(customer2);

        try {
            List<Integer> list = connector.insertIntoCustomers(customers);
            for (int i: list)
                System.out.println(i);
        }
        catch (BankException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public List<Customer> getCustomers() throws BankException {
        List<Customer> customers = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            String selectQuery = "select * from customers where active=1";

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
        } catch (SQLException exception) {
            throw new BankException("Unable to retrieve users at the moment", exception);
        }
    }

    public List<Account> getAccounts() throws BankException {
        List<Account> accounts = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            String selectQuery = "select * from accounts where active=1";
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
        } catch (SQLException exception) {
            throw new BankException("Unable to retrieve accounts at the moment", exception);
        }
    }

    public long insertIntoAccounts(Account account) throws BankException {
        if (account == null) {
            throw new BankException("Invalid account details");
        }

        String query = "insert into accounts (userid, balance, branch, creation_time) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setLong(2, account.getBalance());
            preparedStatement.setString(3, account.getBranch());
            preparedStatement.setLong(4, account.getTime());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException exception) {
            throw new BankException("Adding account failed", exception);
        }
        return -1;
    }

    public int insertIntoCustomers(Customer customer) throws BankException {
        if (customer == null) {
            throw new BankException("Invalid customer details");
        }

        String query = "insert into customers (name, mobile, address, creation_time) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setLong(2, customer.getMobileNumber());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setLong(4, customer.getTime());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException exception) {
            throw new BankException("Adding user failed", exception);
        }
        return -1;
    }


    public List<Long> insertIntoAccounts(List<Account> accounts) throws BankException {
        if (accounts == null || accounts.size() == 0) {
            throw new BankException("Empty batch");
        }

        String query = "insert into accounts (userid, balance, branch, creation_time) values (?,?,?,?)";
        int count = 0;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            List<Long> list = new ArrayList<>();
            getConnection().setAutoCommit(false);
            for (Account account : accounts) {
                preparedStatement.setInt(1, account.getUserId());
                preparedStatement.setLong(2, account.getBalance());
                preparedStatement.setString(3, account.getBranch());
                preparedStatement.setLong(4, account.getTime());
                preparedStatement.addBatch();
            }
            count = preparedStatement.executeBatch().length;
            getConnection().commit();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    list.add(resultSet.getLong(1));
                }
                return list;
            }
        } catch (SQLException exception) {
            throw new BankException("Added " + count + " records", exception);
        }
    }

    public List<Integer> insertIntoCustomers(List<Customer> customers) throws BankException {
        if (customers == null || customers.size() == 0) {
            throw new BankException("Empty batch");
        }

        String query = "insert into customers (name, mobile, address, creation_time) values (?,?,?,?)";
        int count = 0;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            getConnection().setAutoCommit(false);
            List<Integer> list = new ArrayList<>();
            for (Customer customer : customers) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setLong(2, customer.getMobileNumber());
                preparedStatement.setString(3, customer.getAddress());
                preparedStatement.setLong(4, customer.getTime());
                preparedStatement.addBatch();
            }
            count = preparedStatement.executeBatch().length;
            getConnection().commit();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    list.add(resultSet.getInt(1));
                }
                return list;
            }
        } catch (SQLException exception) {
            throw new BankException("Added " + count + " records", exception);
        }
    }

    public long withdrawMoney(long accountNumber, long amount) throws BankException {
        if (!Validator.validate(String.valueOf(accountNumber), String.valueOf(amount))) {
            throw new BankException("Invalid fields");
        }
        if (Validator.validateMoney(amount)) {
            throw new BankException("Amount should be in multiples of 100");
        }

        String query = "update accounts set balance = ? where acc_no = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            long oldBalance = getBalance(accountNumber);
            preparedStatement.setLong(1, oldBalance-amount);
            preparedStatement.setLong(2, accountNumber);
            preparedStatement.executeUpdate();
            return getBalance(accountNumber);
        } catch (SQLException exception) {
            throw new BankException("Money Withdrawal failed", exception);
        }
    }

    public long depositMoney(long accountNumber, long amount) throws BankException {
        if (!Validator.validate(String.valueOf(accountNumber), String.valueOf(amount))) {
            throw new BankException("Invalid fields");
        }
        if (Validator.validateMoney(amount)) {
            throw new BankException("Amount should be in multiples of 100");
        }

        String query = "update accounts set balance = ? where acc_no = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            long oldBalance = getBalance(accountNumber);
            preparedStatement.setLong(1, oldBalance+amount);
            preparedStatement.setLong(2, accountNumber);
            preparedStatement.executeUpdate();
            return getBalance(accountNumber);
        } catch (SQLException exception) {
            throw new BankException("Money deposit failed", exception);
        }
    }

    private static long getBalance(long accountNumber) throws BankException {
        if (!Validator.validate(String.valueOf(accountNumber))) {
            throw new BankException("Invalid account number");
        }

        try (Statement statement = getConnection().createStatement()) {
            String selectQuery = "select balance from accounts where acc_no = " + accountNumber;
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                return -1;
            }
        } catch (SQLException exception) {
            throw new BankException("Could not load balance", exception);
        }
    }

    public boolean deactivateAccount (long accountNumber) throws BankException {
        if (!Validator.validate(String.valueOf(accountNumber))) {
            throw new BankException("Invalid account number");
        }

        String query = "update accounts set active = 0 where acc_no = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new BankException("Deleting account failed", exception);
        }
    }

    public boolean deactivateUser (int userid) throws BankException {
        if (!Validator.validate(String.valueOf(userid))) {
            throw new BankException("Invalid user id");
        }

        String query = "update customers set active = 0 where userid = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1, userid);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new BankException("Deleting account failed", exception);
        }

    }

    public List<Integer> getUsersWithNoActiveAccounts() throws BankException {
        List<Integer> users = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            String selectQuery = "select distinct b.userid from customers a, accounts b where \n" +
                                 "a.userid = b.userid and a.active = 1 and b.active = 0";

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    users.add(userId);
                }
                return users;
            }
        } catch (SQLException exception) {
            throw new BankException("Unable to retrieve accounts at the moment", exception);
        }
    }
}
