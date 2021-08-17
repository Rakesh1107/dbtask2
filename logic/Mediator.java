package logic;

import cache.Cache;
import persistence.Connector;
import exception.BankException;
import pojo.Account;
import pojo.Customer;
import java.util.List;

public class Mediator {

    public static void load() throws BankException {
        List<Account> accounts = Connector.getAccounts();
        List<Integer> usersWithNoActiveAccounts = Connector.getUsersWithNoActiveAccounts();
        List<Customer> customers = Connector.getCustomers();

        Cache.addToCache(accounts);
        Cache.addUsersWithNoActiveAccounts(usersWithNoActiveAccounts);
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

    public static long updateBalance(int option, long accountNumber, long amount) throws BankException {
        if (option == 1) {
            return Connector.withdrawMoney(accountNumber, amount);
        } else {
            return Connector.depositMoney(accountNumber, amount);
        }
    }

    public static boolean deactivateAccount(long accountNumber) throws BankException {
        return Connector.deactivateAccount(accountNumber);
    }

    public static boolean deactivateUser(int userid) throws BankException {
        return Connector.deactivateUser(userid);
    }
}
