package logic;

import db.Query;
import db.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Mediator {

    public static void start() throws SQLException {
        ResultSet accounts = Result.getAccounts();

        while(accounts.next()) {
            DataStorage.addData(accounts);
        }

        ResultSet customers = Result.getCustomers();

        while (customers.next()) {
            DataStorage.addCustomer(customers);
        }
    }

    public static void insertCustomer(Customer customer) throws SQLException {
        Query.insertIntoCustomers(customer);
    }

    public static void insertAccount(Account account) throws SQLException {
        Query.insertIntoAccounts(account);
    }

    public static void insertCustomers(List<Customer> customers) throws SQLException {
        Query.insertIntoCustomers(customers);
    }

    public static void insertAccounts(List<Account> accounts) throws SQLException {
        Query.insertIntoAccounts(accounts);
    }
}
