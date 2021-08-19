package logic;

import cache.Cache;
import pojo.Account;
import pojo.Customer;
import exception.BankException;
import persistence.Persistence;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public enum Mediator {
    INSTANCE;

    Cache cache = Cache.INSTANCE;

    public static Map<String, Persistence> loadProps() throws BankException {
        Map<String, Persistence> map = new HashMap<>();
            try (FileReader reader = new FileReader("config.properties")) {
                Properties p = new Properties();
                p.load(reader);
                for (Map.Entry entry: p.entrySet()) {
                    String key = (String) entry.getKey();

                    Class<?> cls = Class.forName((String) entry.getValue());
                    Persistence prs = (Persistence) cls.newInstance();
                    map.put(key, prs);
                }
            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
                exception.printStackTrace();
                throw new BankException("Failed to load properties file", exception);
            }
        return map;
    }

    public void load() throws BankException {

        if (loadProps().isEmpty()) {
            throw new BankException("Exiting application");
        }

        cache.addToLayers(loadProps());

        for (Persistence prs : cache.getLayers().values()) {
            List<Account> accounts = prs.getAccounts();
            List<Integer> usersWithNoActiveAccounts = prs.getUsersWithNoActiveAccounts();
            List<Customer> customers = prs.getCustomers();

            cache.addToCache(accounts);
            cache.addUsersWithNoActiveAccounts(usersWithNoActiveAccounts);
            cache.addToUsers(customers);
        }

    }

    public int insertCustomer(Customer customer) throws BankException {
        int userId = 0;
        for (Persistence prs: cache.getLayers().values()) {
            userId = prs.insertIntoCustomers(customer);
        }
        return userId;
    }

    public long insertAccount(Account account) throws BankException {
        long accountNumber = 0;
        for (Persistence prs: cache.getLayers().values()) {
            accountNumber = prs.insertIntoAccounts(account);
        }
        return accountNumber;
    }

    public List<Integer> insertCustomers(List<Customer> customers) throws BankException {
        List<Integer> userIds = null;
        for (Persistence prs: cache.getLayers().values()) {
            userIds = prs.insertIntoCustomers(customers);
        }
        return userIds;
    }

    public List<Long> insertAccounts(List<Account> accounts) throws BankException {
        List<Long> accountNumbers = null;
        for (Persistence prs: cache.getLayers().values()) {
            accountNumbers = prs.insertIntoAccounts(accounts);
        }
        return accountNumbers;
    }

    public long updateBalance(int option, long accountNumber, long amount) throws BankException {
        long balance = 0;
        if (option == 1) {
            for (Persistence prs: cache.getLayers().values()) {
                balance = prs.withdrawMoney(accountNumber, amount);
            }
        } else {
            for (Persistence prs: cache.getLayers().values()) {
                balance = prs.depositMoney(accountNumber, amount);
            }
        }
        return balance;
    }

    public boolean deactivateAccount(long accountNumber) throws BankException {
        boolean done = false;
        for (Persistence prs: cache.getLayers().values()) {
            done = prs.deactivateAccount(accountNumber);
        }
        return done;
    }

    public boolean deactivateUser(int userid) throws BankException {
        boolean done = false;
        for (Persistence prs: cache.getLayers().values()) {
            done = prs.deactivateUser(userid);
        }
        return done;
    }
}
