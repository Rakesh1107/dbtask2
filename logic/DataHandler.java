package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHandler {

    public static long[] createNewUser(String name, long mobileNumber, String address, String branch) {
        int userId = Generator.generateUserId();
        long accountNumber = Generator.generateAccountNumber();

        Customer customer;
        customer = new Customer();
        customer.setUserId(userId);
        customer.setName(name);
        customer.setMobileNumber(mobileNumber);
        customer.setAddress(address);

        Account account;
        account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBranch(branch);

        Mediator.insertCustomer(customer);
        Mediator.insertAccount(account);

        DataStorage.getData().put(userId, new HashMap<>());
        DataStorage.getData().get(userId).put(accountNumber, account);
        DataStorage.getUsers().put(userId, customer);
        DataStorage.getAccounts().add(accountNumber);

        return new long[]{userId, accountNumber};
    }

    public static long createNewAccount(int userId, String branch) {

        if (!DataStorage.getData().containsKey(userId)) {
            return -1;
        }

        long accountNumber = Generator.generateAccountNumber();

        Account account;
        account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setBranch(branch);

        Mediator.insertAccount(account);

        DataStorage.getData().get(userId).put(accountNumber, account);
        DataStorage.getAccounts().add(accountNumber);

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
