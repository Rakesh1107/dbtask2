package cache;

import pojo.Account;
import pojo.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cache {

    private static final Map<Integer, Map<Long, Account>> cache = new HashMap<>();
    private static final Map<Integer, Customer> users = new HashMap<>();
    //private static final List<Long> userAccounts = new ArrayList<>();

    public static void addToCache(List<Account> accounts) {
        for (Account account : accounts) {
            int userId = account.getUserId();
            long accountNumber = account.getAccountNumber();
            Map<Long, Account> accountMap = cache.getOrDefault(userId, new HashMap<>());
            cache.put(userId, accountMap);
            accountMap.put(accountNumber, account);
            //userAccounts.add(accountNumber);
        }
    }

    public static void addUsersWithNoActiveAccounts(List<Integer> list) {
        for(int userId: list) {
            cache.put(userId, new HashMap<>());
        }
    }

    public static void addToUsers(List<Customer> customers) {
        for (Customer customer : customers) {
            int userId = customer.getUserId();
            users.put(userId, customer);
        }
    }

    public static Map<Integer, Map<Long, Account>> getCache() {
        return cache;
    }

    public static Map<Integer, Customer> getUsers() {
        return users;
    }

}
