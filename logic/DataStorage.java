package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {

    private static final Map<Integer, Map<Long, Account>> data = new HashMap<>();
    private static final Map<Integer, Customer> users = new HashMap<>();
    private static final List<Long> userAccounts = new ArrayList<>();

    public static void addData(List<Account> accounts)  {
        for (Account account: accounts) {
            int userId = account.getUserId();
            long accountNumber = account.getAccountNumber();
            if (!data.containsKey(userId)) {
                data.put(userId, new HashMap<>());
            }
            userAccounts.add(accountNumber);
            data.get(userId).put(accountNumber, account);
        }
    }

    public static void addCustomer(List<Customer> customers)  {
        for (Customer customer: customers) {
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

    public static Map<Integer, Customer> getUsers() { return users; }

}
