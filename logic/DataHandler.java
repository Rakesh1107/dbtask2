package logic;

import pojo.Account;
import pojo.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHandler {

    public static long[] createNewUser(String name, long mobileNumber, String address, String branch) {

        int userId = Mediator.insertCustomer(name, mobileNumber, address);
        long accountNumber = Mediator.insertAccount(userId, branch);

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setName(name);
        customer.setMobileNumber(mobileNumber);
        customer.setAddress(address);

        Account account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBranch(branch);

        if(userId != -1 && accountNumber != -1) {
            DataStorage.getUsers().put(userId, customer);
            DataStorage.getData().put(userId, new HashMap<>());
            DataStorage.getData().get(userId).put(accountNumber, account);
            DataStorage.getAccounts().add(accountNumber);
        }

        return new long[]{userId, accountNumber};

    }

    public static long createNewAccount(int userId, String branch) {

        if (!DataStorage.getData().containsKey(userId)) {
            return -1;
        }

        long accountNumber = Mediator.insertAccount(userId, branch);

        Account account;
        account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBranch(branch);

        if (accountNumber != -1) {
            DataStorage.getData().get(userId).put(accountNumber, account);
            DataStorage.getAccounts().add(accountNumber);
        }

        return accountNumber;

    }

    public static long checkBalance(int userId) {

        if (!DataStorage.getData().containsKey(userId)) {
            return -1;
        }

        long balance = 0;
        for (Account account : DataStorage.getData().get(userId).values()) {
            balance += account.getBalance();
        }
        return balance;
    }

    public static List<Account> showAccounts(int userId) {

        if (!DataStorage.getUsers().containsKey(userId)) {
            return null;
        }

        if (!DataStorage.getData().containsKey(userId)) {
            return new ArrayList<>();
        }

        return new ArrayList<>(DataStorage.getData().get(userId).values());
    }
}
