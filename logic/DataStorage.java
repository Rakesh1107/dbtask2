package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {

    static Map<Integer, Map<Long, Account>> data = new HashMap<>();
    static Map<Integer, Customer> users = new HashMap<>();
    static List<Long> accounts = new ArrayList<>();

    public static void addData(ResultSet accountData) throws SQLException {
        int userId = accountData.getInt(2);
        long accountNumber = accountData.getLong(3);
        long balance = accountData.getLong(4);
        String branch = accountData.getString(5);

        Account account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setBranch(branch);

        accounts.add(accountNumber);

        if(data.containsKey(userId)) {
            Map<Long, Account> newAccount = data.get(userId);
            newAccount.put(accountNumber, account);
        }
        else {
            data.put(userId, new HashMap<>());
            data.get(userId).put(accountNumber, account);
        }
    }

    public static Map<Integer, Map<Long, Account>> getData() {
        return data;
    }

    public static List<Long> getAccounts() {
        return accounts;
    }

    public static Map<Integer, Customer> getUsers() { return users; }

    public static void addCustomer(ResultSet customersData) throws SQLException {
        int userId = customersData.getInt(1);
        String name = customersData.getString(2);
        long mobileNumber = customersData.getLong(3);
        String address = customersData.getString(4);

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setName(name);
        customer.setMobileNumber(mobileNumber);
        customer.setAddress(address);

        users.put(userId, customer);

    }
}
