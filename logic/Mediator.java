package logic;

import cache.Cache;
import db.Connector;
import exception.BankException;
import pojo.Account;
import pojo.Customer;
import java.util.List;

public class Mediator {

    public static void extract() throws BankException {
        List<Account> accounts = Connector.getAccounts();
        Cache.addToCache(accounts);

        List<Customer> customers = Connector.getCustomers();
        Cache.addToUsers(customers);
    }

    public static int insertCustomer(Customer customer) throws BankException {
        return Connector.insertIntoCustomers(customer);
    }

    public static long insertAccount(Account account) throws BankException {
        return Connector.insertIntoAccounts(account);
    }

    public static List<Integer> insertCustomers(List<Customer> customers) throws BankException {
        return Connector.insertIntoCustomers(customers);
    }

    public static List<Integer> insertAccounts(List<Account> accounts) throws BankException {
        return Connector.insertIntoAccounts(accounts);
    }
}
