package logic;

import cache.Cache;
import db.Connector;
import pojo.Account;
import pojo.Customer;
import java.util.List;

public class Mediator {

    public static void extract() {
        List<Account> accounts = Connector.getAccounts();
        Cache.addToCache(accounts);

        List<Customer> customers = Connector.getCustomers();
        Cache.addCustomer(customers);
    }

    public static int insertCustomer(Customer customer) {
        return Connector.insertIntoCustomers(customer);
    }

    public static long insertAccount(Account account) {
        return Connector.insertIntoAccounts(account);
    }

    public static boolean insertCustomers(List<Customer> customers) {
        return Connector.insertIntoCustomers(customers);
    }

    public static boolean insertAccounts(List<Account> accounts) {
        return Connector.insertIntoAccounts(accounts);
    }
}
