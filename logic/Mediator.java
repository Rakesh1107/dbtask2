package logic;

import db.Query;
import db.Result;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mediator {

    public static void start() throws SQLException {
        ResultSet accounts = Result.getAccounts();

        while(accounts.next()) {
            DataStorage.addData(accounts);
        }
    }


    public static void insertCustomer(Customer customer) throws SQLException {
        Query.insertIntoCustomers(customer);
    }

    public static void insertAccount(Account account) throws SQLException {
        Query.insertIntoAccounts(account);
    }
}
