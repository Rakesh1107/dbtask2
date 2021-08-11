package logic;

import pojo.Account;
import pojo.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {

    private static final Map<Integer, Map<Long, Account>> data = new HashMap<>();
    private static final Map<Integer, Customer> users = new HashMap<>();
    private static final List<Long> userAccounts = new ArrayList<>();

    public static void addData(List<Account> accounts) {
        for (Account account : accounts) {
            int userId = account.getUserId();
            long accountNumber = account.getAccountNumber();
            Map<Long, Account> accountMap = data.getOrDefault(userId, new HashMap<>());
            data.put(userId, accountMap);
            accountMap.put(accountNumber, account);
            userAccounts.add(accountNumber);
        }
    }

    public static void addCustomer(List<Customer> customers) {
        for (Customer customer : customers) {
            int userId = customer.getUserId();
            users.put(userId, customer);
        }
    }

    public static Map<Integer, Map<Long, Account>> getData() {
        return data;
    }

    public static List<Long> getAccounts() {
        return userAccounts;
    }

    public static Map<Integer, Customer> getUsers() {
        return users;
    }

}
