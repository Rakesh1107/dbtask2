package cache;

import pojo.Account;
import pojo.Customer;
import persistence.Persistence;

import java.util.*;

public class Cache {

    private static final Map<Integer, Map<Long, Account>> cache = new HashMap<>();
    private static final Map<Integer, Customer> users = new HashMap<>();
    private static final List<Persistence> layers = new ArrayList<>();

    // Used in case of generating random account numbers
    //private static final List<Long> userAccounts = new ArrayList<>();

    // Adding to cache
    public static void addToCache(List<Account> accounts) {
        for (Account account : accounts) {
            int userId = account.getUserId();
            long accountNumber = account.getAccountNumber();
            Map<Long, Account> accountMap = cache.getOrDefault(userId, new HashMap<>());
            accountMap.put(accountNumber, account);
            cache.put(userId, accountMap);
            //userAccounts.add(accountNumber);
        }
    }

    public static void addUsersWithNoActiveAccounts(List<Integer> list) {
        for(int userId: list) {
            cache.put(userId, new HashMap<>());
        }
    }

    // Adding to users
    public static void addToUsers(List<Customer> customers) {
        for (Customer customer : customers) {
            int userId = customer.getUserId();
            users.put(userId, customer);
        }
    }

    // Adding to layers
    public static void addToLayers(List<Persistence> persistenceLayers) {
        layers.addAll(persistenceLayers);
    }

    public static Map<Integer, Map<Long, Account>> getCache() {
        return cache;
    }

    public static Map<Integer, Customer> getUsers() {
        return users;
    }

    public static List<Persistence> getLayers() {
        return layers;
    }
}
