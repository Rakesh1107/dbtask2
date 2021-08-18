package logic;

import cache.Cache;
import persistence.MySQLConnector;
import exception.BankException;
import persistence.Persistence;
import pojo.Account;
import pojo.Customer;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Mediator {


    public static List<Persistence> loadProps() {
        List<Persistence> set = new ArrayList<>();
            try (FileReader reader = new FileReader("config.properties")) {
                Properties p = new Properties();
                p.load(reader);
                for (Object layer: p.values()) {
                    Class<?> cls = Class.forName((String) layer);
                    Persistence prs = (Persistence) cls.newInstance();
                    set.add(prs);
                }
            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        return set;
    }

    public void load() throws BankException {
        List<Persistence> layers = loadProps();

        if (layers.isEmpty()) {
            throw new BankException("Exiting application");
        }

        MySQLConnector connector = new MySQLConnector();
        List<Account> accounts = connector.getAccounts();
        List<Integer> usersWithNoActiveAccounts = connector.getUsersWithNoActiveAccounts();
        List<Customer> customers = connector.getCustomers();

        Cache.addToLayers(layers);
        Cache.addToCache(accounts);
        Cache.addUsersWithNoActiveAccounts(usersWithNoActiveAccounts);
        Cache.addToUsers(customers);

    }

    public int insertCustomer(Customer customer) throws BankException {
        int userId = 0;
        for (Persistence prs: Cache.getLayers()) {
            userId = prs.insertIntoCustomers(customer);
        }
        return userId;
    }

    public long insertAccount(Account account) throws BankException {
        long accountNumber = 0;
        for (Persistence prs: Cache.getLayers()) {
            accountNumber = prs.insertIntoAccounts(account);
        }
        return accountNumber;
    }

    public List<Integer> insertCustomers(List<Customer> customers) throws BankException {
        List<Integer> userIds = null;
        for (Persistence prs: Cache.getLayers()) {
            userIds = prs.insertIntoCustomers(customers);
        }
        return userIds;
    }

    public List<Long> insertAccounts(List<Account> accounts) throws BankException {
        List<Long> accountNumbers = null;
        for (Persistence prs: Cache.getLayers()) {
            accountNumbers = prs.insertIntoAccounts(accounts);
        }
        return accountNumbers;
    }

    public long updateBalance(int option, long accountNumber, long amount) throws BankException {
        long balance = 0;
        if (option == 1) {
            for (Persistence prs: Cache.getLayers()) {
                balance = prs.withdrawMoney(accountNumber, amount);
            }
        } else {
            for (Persistence prs: Cache.getLayers()) {
                balance = prs.depositMoney(accountNumber, amount);
            }
        }
        return balance;
    }

    public boolean deactivateAccount(long accountNumber) throws BankException {
        boolean done = false;
        for (Persistence prs: Cache.getLayers()) {
            done = prs.deactivateAccount(accountNumber);
        }
        return done;
    }

    public boolean deactivateUser(int userid) throws BankException {
        boolean done = false;
        for (Persistence prs: Cache.getLayers()) {
            done = prs.deactivateUser(userid);
        }
        return done;
    }
}
